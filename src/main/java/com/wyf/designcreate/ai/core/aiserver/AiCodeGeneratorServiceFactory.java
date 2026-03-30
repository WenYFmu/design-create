package com.wyf.designcreate.ai.core.aiserver;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AiCodeGeneratorServiceFactory {

    /**
     * 使用openAI协议调用模型
     */
    @Resource
    private ChatModel openAiChatModel;

    @Bean
    public AiCodeGeneratorService aiCodeGeneratorService() {
        return AiServices.create(AiCodeGeneratorService.class, openAiChatModel);
    }

}
