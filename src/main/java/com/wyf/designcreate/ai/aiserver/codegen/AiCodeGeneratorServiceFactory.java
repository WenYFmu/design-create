package com.wyf.designcreate.ai.aiserver.codegen;

import com.wyf.designcreate.ai.memory.MyChatMemoryStore;
import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiCodeGeneratorServiceFactory {

    /**
     * 使用openAI协议调用模型
     */
    @Resource
    private ChatModel openAiChatModel;

    @Resource
    private StreamingChatModel streamOpenAiChatModel;

    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    @Resource
    private MyChatMemoryStore myChatMemoryStore;
    @Bean
    public AiCodeGeneratorService aiCodeGeneratorService() {
        return AiServices.builder(AiCodeGeneratorService.class)
                .chatModel(openAiChatModel)
                .streamingChatModel(streamOpenAiChatModel)
                .chatMemoryProvider(memoryId ->
                        MessageWindowChatMemory.builder()
                                .chatMemoryStore(myChatMemoryStore)
                                .maxMessages(20)
                                .id(memoryId)
                                .build()
                )
                .build();
    }

}