package com.wyf.designcreate.ai.aiserver.summary;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SummaryChatModel implements ChatModel {
    
    @Value("${ai.summary.system-prompt-path}")
    private Resource systemPromptResource;
    
    private String systemPrompt;
    
    @PostConstruct
    public void init() {
        try (InputStream inputStream = systemPromptResource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            systemPrompt = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load system prompt from: " + systemPromptResource, e);
        }
    }
    @Override
    public ChatResponse chat(List<ChatMessage> messages) {
        messages.addFirst(SystemMessage.from(systemPrompt));
        return ChatModel.super.chat(messages);
    }

}
