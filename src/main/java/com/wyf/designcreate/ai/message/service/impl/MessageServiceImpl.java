package com.wyf.designcreate.ai.message.service.impl;

import java.time.LocalDateTime;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyf.designcreate.ai.aiserver.summary.AiSummaryProcessService;
import com.wyf.designcreate.ai.memory.MemoryProperties;
import com.wyf.designcreate.ai.message.service.MessageSummaryService;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.constant.MessageConstant;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.exception.ThrowUtil;
import com.wyf.designcreate.model.dto.app.ChatHistoryQueryRequest;
import com.wyf.designcreate.model.entity.App;
import com.wyf.designcreate.model.entity.Message;
import com.wyf.designcreate.ai.message.service.MessageService;
import com.wyf.designcreate.mapper.MessageMapper;
import com.wyf.designcreate.model.entity.MessageSummary;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.service.AppService;
import com.wyf.designcreate.service.UserService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageType;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author 15502
 * @description 针对表【message(应用会话消息记录表)】的数据库操作Service实现
 * @createDate 2026-04-06 14:40:04
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
        implements MessageService {

    @Resource
    private MessageSummaryService messageSummaryService;

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private AppService appService;

    @Resource
    private MemoryProperties memoryProperties;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private AiSummaryProcessService aiSummaryProcessService;

    @Resource
    private Executor memorySummaryThreadPoolExecutor;

    @Override
    public ChatMessage loadLatestSummary(Long appId) {
        // 查询最新的摘要
        MessageSummary messageSummary = messageSummaryService.queryLatestSummary(appId);
        if (messageSummary == null) {
            return null;
        }
        String content = messageSummary.getContent();
        return AiMessage.from(content);
    }

    @Override
    public List<ChatMessage> loadHistoryMessage(Long appId) {
        // 查询指定轮次
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("appId", appId)
                .orderByDesc("id")
                .last("limit " + memoryProperties.getHistoryKeepTurns() * 2);
        List<Message> messages = this.list(queryWrapper);
        if (messages.isEmpty()) {
            return List.of();
        }
        return toHistoryMessages(messages.reversed());
    }

    @Override
    public Page<Message> listPageHistoryMessage(ChatHistoryQueryRequest chatHistoryQueryRequest, User loginUser) {
        Long id = chatHistoryQueryRequest.getId();
        String message = chatHistoryQueryRequest.getMessage();
        String messageType = chatHistoryQueryRequest.getMessageType();
        Long appId = chatHistoryQueryRequest.getAppId();
        Long userId = chatHistoryQueryRequest.getUserId();
        Date lastCreateTime = chatHistoryQueryRequest.getLastCreateTime();
        int current = chatHistoryQueryRequest.getCurrent();
        int pageSize = chatHistoryQueryRequest.getPageSize();
        String sortField = chatHistoryQueryRequest.getSortField();
        String sortOrder = chatHistoryQueryRequest.getSortOrder();
        App app = appService.getById(appId);
        ThrowUtil.throwIf(app == null, new BusinessException(ErrorCode.NULL_ERROR, "应用不存在"));
        //所有人均可查看对话历史 todo 已部署的应用才可以查看
        // 展示使用【游标查询】
        Page<Message> messagePage = new Page<>();
        messagePage.setCurrent(current);
        messagePage.setSize(pageSize);
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("appId", appId)
                .eq(id != null, "id", id)
                .eq(StrUtil.isNotBlank(messageType), "messageType", messageType)
                .eq(userId != null, "userId", userId)
                .like(StrUtil.isNotBlank(message), "message", message)
                .lt(lastCreateTime != null, "createTime", lastCreateTime)
                .orderByDesc("id");
        Page<Message> page = this.page(messagePage, queryWrapper);
        messagePage.setRecords(page.getRecords().reversed());
        return messagePage;
    }

    @Override
    public boolean compressIfNeeded(Long appId, ChatMessage message, int size) {
        if (!memoryProperties.getSummaryEnabled()) {
            return false;
        }
        // 只有助手消息才触发压缩检查
        // 原因：一轮对话包含用户消息+助手消息，只在助手消息时触发避免重复检查
        if (message.type() != ChatMessageType.AI) {
            return false;
        }
        if (size < memoryProperties.getSummaryStartTurns() * 2) {
            return false;
        }
        // 异步执行压缩任务，不阻塞主流程
        // 使用专用线程池，避免影响其他业务
        CompletableFuture.runAsync(() -> doCompressIfNeeded(appId), memorySummaryThreadPoolExecutor)
                .exceptionally(ex -> {
                    log.error("对话记忆摘要异步任务失败 - appId: {}",
                            appId, ex);
                    return null;
                });
        return true;
    }

    private void doCompressIfNeeded(Long appId) {
        long startTime = System.currentTimeMillis();
        String lockKey = "message:compress:lock:" + appId;
        //获取锁
        RLock lock = redissonClient.getLock(lockKey);
        if (!lock.tryLock()) {
            return;
        }
        try {
            // 如果有历史摘要则拼接再摘要
            MessageSummary messageSummary = messageSummaryService.queryLatestSummary(appId);
            // 不进行摘要的历史对话
            List<Message> historyMessage = this.listLatestAppMessage(appId, memoryProperties.getHistoryKeepTurns() * 2);
            if (historyMessage.isEmpty()) {
                return;
            }
            Message lastMessage = historyMessage.getLast();
            // cutOffId之后的对话都进行压缩
            Long cutOffId = lastMessage.getId();
            Long afterId = messageSummary == null ? null : messageSummary.getLastMessageId();
            //获取需要压缩的对话 (cutOffId, afterId)间的对话
            List<Message> toSummarize = this.listBetweenAppMessage(appId, cutOffId, afterId);
            if (toSummarize.isEmpty()) {
                return;
            }
            String existingSummary = messageSummary == null ? null : messageSummary.getContent();
            String summary = summarizeMessages(toSummarize, existingSummary);
            // 获取摘要总结的 afterId 插入数据库
            Long lastMessageId = toSummarize.getLast().getId();
            App app = appService.getById(appId);
            Long userId = app.getUserId();
            MessageSummary newMessageSummary = new MessageSummary();
            newMessageSummary.setAppId(appId);
            newMessageSummary.setUserId(userId);
            newMessageSummary.setLastMessageId(lastMessageId);
            newMessageSummary.setContent(summary);
            messageSummaryService.save(newMessageSummary);
            log.info("摘要成功 - appId：{}，userId：{}，消息数：{}，耗时：{}ms",
                    appId, userId, toSummarize.size(),
                    System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("摘要失败 - appId：{}", appId, e);
        } finally {
            // 释放分布式锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }


    private String summarizeMessages(List<Message> messages, String existingSummary) {
        // 将数据库消息转换为ChatMessage格式
        List<ChatMessage> histories = toHistoryMessages(messages);
        if (CollUtil.isEmpty(histories)) {
            return existingSummary;
        }
        int summaryMaxChars = memoryProperties.getSummaryMaxChars();
        List<ChatMessage> summaryMessages = new ArrayList<>();


        // 2. Assistant消息（可选）：历史摘要
        // 作为上下文传入，让LLM能够增量合并，避免重复
        if (StrUtil.isNotBlank(existingSummary)) {
            summaryMessages.add(AiMessage.from(
                    "历史摘要（仅用于合并去重，不得作为事实新增来源；若与本轮对话冲突，以本轮对话为准）：\n"
                            + existingSummary.trim()
            ));
        }

        // 3. 添加需要摘要的对话历史
        summaryMessages.addAll(histories);

        // 4. User消息：生成指令
        summaryMessages.add(UserMessage.from(
                "合并以上对话与历史摘要，去重后输出更新摘要。要求：严格≤" + summaryMaxChars + "字符；仅一行。"
        ));
        // 调用压缩服务
        try {
            String summary = aiSummaryProcessService.extractSummary(summaryMessages, Integer.toString(summaryMaxChars));
            log.info("对话摘要生成 - resultChars: {}", summary.length());
            return summary;
        } catch (Exception e) {
            log.error("对话记忆摘要生成失败, appId相关消息数: {}", messages.size(), e);
            // 失败时返回原有摘要，保证服务可用性
            return existingSummary;
        }
    }


    @Override
    public List<ChatMessage> toHistoryMessages(List<Message> messages) {
        if (CollUtil.isEmpty(messages)) {
            return List.of();
        }
        return messages.stream()
                .filter(item -> item != null
                        && StrUtil.isNotBlank(item.getContent()))
                .map(item -> {
                    String type = item.getType().toLowerCase();
                    if (MessageConstant.USER.equals(type)) {
                        return UserMessage.from(item.getContent());
                    } else if (MessageConstant.AI.equals(type)) {
                        return AiMessage.from(item.getContent());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> listLatestAppMessage(Long appId, int limit) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("appId", appId)
                .orderByDesc("createTime")
                .last("limit " + limit);
        return this.list(queryWrapper);
    }

    @Override
    public List<Message> listBetweenAppMessage(Long appId, Long cutOffId, Long afterId) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("appId", appId)
                .gt(cutOffId != null, "id", cutOffId)
                .lt(afterId != null, "id", afterId)
                .orderByAsc("createTime");// 老->新
        return this.list(queryWrapper);
    }

}




