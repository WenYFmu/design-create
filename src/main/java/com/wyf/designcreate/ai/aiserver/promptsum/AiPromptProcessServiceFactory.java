package com.wyf.designcreate.ai.aiserver.promptsum;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiPromptProcessServiceFactory {

    @Resource
    private ChatModel lightWeightChatModel;

    @Bean
    public AiPromptProcessService aiPromptProcessService() {
        return AiServices.builder(AiPromptProcessService.class)
                .chatModel(lightWeightChatModel)
                .build();
    }
}
