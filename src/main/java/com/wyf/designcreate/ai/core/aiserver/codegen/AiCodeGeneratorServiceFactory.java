package com.wyf.designcreate.ai.core.aiserver.codegen;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
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

    @Bean
    public AiCodeGeneratorService aiCodeGeneratorService() {
        return AiServices.builder(AiCodeGeneratorService.class)
                .chatModel(openAiChatModel)
                .streamingChatModel(streamOpenAiChatModel)
                .build();
    }

}