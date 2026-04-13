package com.wyf.designcreate.ai.aiserver.summary;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiSummaryProcessServiceFactory {
    @Resource
    private ChatModel openAiChatModel;
    @Bean
    public AiSummaryProcessService aiSummaryProcessService() {
        return AiServices.builder(AiSummaryProcessService.class)
                .chatModel(openAiChatModel)
                .build();
    }
}
