package com.wyf.designcreate.ai.aiserver.summary;

import com.wyf.designcreate.model.entity.Message;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SummaryChatModelTest {

    @Resource
    private SummaryChatModel summaryChatModel;

    @Test
    void chat() {
        LinkedList<ChatMessage> messages = new LinkedList<>();
        messages.add(UserMessage.from("你是谁"));
        summaryChatModel.chat(messages);
    }
}