package com.wyf.designcreate.ai.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyf.designcreate.model.dto.app.ChatHistoryQueryRequest;
import com.wyf.designcreate.model.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyf.designcreate.model.entity.User;
import dev.langchain4j.data.message.ChatMessage;

import java.util.List;

/**
* @author 15502
* @description 针对表【message(应用会话消息记录表)】的数据库操作Service
* @createDate 2026-04-06 14:40:04
*/
public interface MessageService extends IService<Message> {

    /**
     * 加载最新的摘要总结
     * @param appId
     * @return
     */
    ChatMessage loadLatestSummary(Long appId);

    /**
     * 加载对话历史记录【对话时使用】
     *
     * @param appId 对话ID
     * @return 对话历史消息列表
     */
    List<ChatMessage> loadHistoryMessage(Long appId);

    /**
     * 加载对话历史记录【展示使用】
     *
     * @param chatHistoryQueryRequest 查询请求
     * @return 对话历史消息列表
     */
    Page<Message> listPageHistoryMessage(ChatHistoryQueryRequest chatHistoryQueryRequest, User loginUser);

    /**
     * 异步执行会话压缩
     *
     * @param appId
     * @param message 检查是否是ai消息，一轮对话以ai消息为结尾
     * @param size
     * @return
     */
    boolean compressIfNeeded(Long appId, ChatMessage message, int size);

    /**
     * 将数据库消息列表转换为ChatMessage列表
     * <p>
     * 只保留USER和ASSISTANT角色的消息，过滤掉其他角色
     *
     * @param messages 数据库消息列表
     * @return ChatMessage列表
     */
    List<ChatMessage> toHistoryMessages(List<Message> messages);

    /**
     * 获取最新的多轮对话
     *
     * @param appId 应用id
     * @param limit 获取前limit条对话
     * @return
     */
    List<Message> listLatestAppMessage(Long appId, int limit);


    /**
     * 获取指定区间的对话
     * @param appId 应用id
     * @param cutOffId 起始
     * @param afterId 终止
     * @return
     */
    List<Message> listBetweenAppMessage(Long appId, Long cutOffId,Long afterId);

}
