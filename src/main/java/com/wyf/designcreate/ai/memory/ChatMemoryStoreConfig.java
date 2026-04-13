package com.wyf.designcreate.ai.memory;

import com.wyf.designcreate.ai.message.service.MessageService;
import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class ChatMemoryStoreConfig {

    private String host;

    private int port;

    private String password;

    private long timeout;

    @Resource
    @Lazy
    private MessageService messageService;

    @Bean
    public RedisChatMemoryStore redisChatMemoryStore() {
        return RedisChatMemoryStore.builder()
                .host(host)
                .port(port)
                .password(password)
                .ttl(timeout)
                .build();
    }
    @Bean
    public MyChatMemoryStore myChatMemoryStore() {
        // timeout 单位是毫秒，转换为秒；如果为0或负数则不设置TTL
        return new MyChatMemoryStore(
                host,
                port,
                password,
                "chat_memory:",  // key前缀
                timeout,
                messageService
        );
    }
}
