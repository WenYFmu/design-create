package com.wyf.designcreate.ai.memory;

import cn.hutool.core.collection.CollUtil;
import com.wyf.designcreate.ai.message.service.MessageService;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.exception.BusinessException;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.data.message.ChatMessageType;
import dev.langchain4j.internal.ValidationUtils;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class MyChatMemoryStore implements ChatMemoryStore {

    private final JedisPooled client;
    private final String keyPrefix;
    private final Long ttl;
    private final MessageService messageService;

    public MyChatMemoryStore(String host, Integer port, String password, String prefix, Long ttl,
                             MessageService messageService) {
        ValidationUtils.ensureNotBlank(host, "host");
        ValidationUtils.ensureNotNull(port, "port");

        if (password != null && !password.isEmpty()) {
            this.client = new JedisPooled(host, port, null, password);
        } else {
            this.client = new JedisPooled(host, port);
        }

        this.keyPrefix = ValidationUtils.ensureNotNull(prefix, "prefix");
        this.ttl = ValidationUtils.ensureNotNull(ttl, "ttl");
        this.messageService = messageService;
        log.info("MyChatMemoryStore 初始化完成 - host:{}, port:{}, prefix:{}, ttl:{}",
                host, port, prefix, ttl);
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String key = toRedisKey(memoryId);
        String json = client.get(key);
        // 缓存未命中【这里不需要手动写缓存 updateMessages方法里写缓存】
        long startTime = System.currentTimeMillis();
        if (json == null) {
            // 从数据库中获取对话记忆
            // 并行加载摘要和历史记录
            CompletableFuture<ChatMessage> summaryMessageFuture = CompletableFuture.supplyAsync(
                    () -> loadSummaryWithFallback(memoryId));
            CompletableFuture<List<ChatMessage>> historyMessageFuture = CompletableFuture.supplyAsync(
                    () -> loadHistoryMessageWithFallback(memoryId));
            return CompletableFuture.allOf(summaryMessageFuture, historyMessageFuture)
                    .thenApply(v -> {
                        ChatMessage summary = summaryMessageFuture.join();
                        List<ChatMessage> history = historyMessageFuture.join();
                        log.debug("加载对话记忆 - memoryId: {}摘要: {}, 历史消息数: {}, 耗时: {}ms",
                                memoryId, summary != null, history.size(), System.currentTimeMillis() - startTime);
                        return attachSummary(summary, history);
                    }).join();
        }

        List<ChatMessage> messages = ChatMessageDeserializer.messagesFromJson(json);
        log.debug("获取记忆成功: {}, 消息数: {}", key, messages.size());
        return messages;
    }

    private ChatMessage loadSummaryWithFallback(Object memoryId) {
        ChatMessage chatMessage = messageService.loadLatestSummary((Long) memoryId);
        if (chatMessage == null) {
            log.warn("没有找到对话摘要，跳过摘要{}", memoryId);
            return null;
        }
        return chatMessage;
    }

    private List<ChatMessage> loadHistoryMessageWithFallback(Object memoryId) {
        List<ChatMessage> chatMessageList = messageService.loadHistoryMessage((Long) memoryId);
        if (chatMessageList == null) {
            log.warn("没有找到历史对话，跳过加载{}", memoryId);
            return null;
        }
        return chatMessageList;
    }

    private List<ChatMessage> attachSummary(ChatMessage summary, List<ChatMessage> messages) {
        // 确保返回值不为 null
        if (CollUtil.isEmpty(messages)) {
            return List.of();
        }
        if (summary == null) {
            return messages;
        }
        List<ChatMessage> result = new ArrayList<>();
        result.add(summary);
        result.addAll(messages);
        return result;
    }
    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        ValidationUtils.ensureNotEmpty(messages, "messages");
        if (messages instanceof LinkedList<ChatMessage> linkedList) {
            if (!linkedList.getFirst().type().equals(ChatMessageType.SYSTEM)) {
                //如果链表头不是系统提示词则将系统提示词提到表头
                ChatMessage systemMessage = linkedList.stream()
                        .filter(chatMessage -> chatMessage.type().equals(ChatMessageType.SYSTEM))
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(ErrorCode.SYSTEM_ERROR, "没有系统提示词"));//没有系统提示词则抛出异常，保存时系统提示词是必须的
                linkedList.remove(systemMessage);
                linkedList.addFirst(systemMessage);
            }

        }
        // 判断是否需要压缩对话【当对话轮次达到限制】
        int size = messages.size();
        ChatMessage chatMessage = messages.getLast();
        boolean ifNeeded = messageService.compressIfNeeded((Long) memoryId, chatMessage, size);
        if (ifNeeded) {
            // 压缩对话，删除redis缓存
            deleteMessages(memoryId);
            return;
        }

        String json = ChatMessageSerializer.messagesToJson(messages);
        String key = toRedisKey(memoryId);
        if (ttl > 0L) {
            try (Pipeline pipeline = client.pipelined()) {
                pipeline.set(key, json);
                pipeline.expire(key, ttl);
                pipeline.syncAndReturnAll();
            }
        } else {
            client.set(key, json);
        }

        log.debug("更新记忆成功: {}, 消息数: {}", key, messages.size());
    }

    @Override
    public void deleteMessages(Object memoryId) {
        String key = toRedisKey(memoryId);
        client.del(key);
        log.debug("删除记忆成功: {}", key);
    }

    private String toMemoryIdString(Object memoryId) {
        boolean isNullOrEmpty = memoryId == null || memoryId.toString().trim().isEmpty();
        if (isNullOrEmpty) {
            throw new IllegalArgumentException("memoryId 不能为空");
        }
        return memoryId.toString();
    }

    private String toRedisKey(Object memoryId) {
        return keyPrefix + toMemoryIdString(memoryId);
    }
}
