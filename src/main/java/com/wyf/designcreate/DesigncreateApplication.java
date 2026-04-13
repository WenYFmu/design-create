package com.wyf.designcreate;

import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {RedisEmbeddingStoreAutoConfiguration.class})
@MapperScan("com.wyf.designcreate.mapper")
public class DesigncreateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesigncreateApplication.class, args);
	}

}

