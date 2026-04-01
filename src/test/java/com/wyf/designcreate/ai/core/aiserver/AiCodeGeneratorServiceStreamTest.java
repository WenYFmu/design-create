package com.wyf.designcreate.ai.core.aiserver;

import cn.hutool.core.lang.Assert;
import com.wyf.designcreate.ai.core.aiserver.codegen.AiCodeGeneratorService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
class AiCodeGeneratorServiceStreamTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    void generateHtmlCodeStream() {
        Flux<String> stringFlux = aiCodeGeneratorService.generateHtmlCodeStream("生成一个HTML页面，内容是“Hello World”");
        StringBuilder stringBuilder = new StringBuilder();
        stringFlux.doOnNext(s -> {
                    System.out.print(s);
                    stringBuilder.append(s);
                })
                .doOnComplete(() -> System.out.println("\n完成！"))
                .collectList()
                .block(); // 阻塞等待完成
        Assert.notBlank(stringBuilder);
    }

    @Test
    void generateMultiFileCodeStream() {
        Flux<String> stringFlux = aiCodeGeneratorService.generateMultiFileCodeStream("生成一个HTML页面，内容是“Hello World”");
        StringBuilder stringBuilder = new StringBuilder();
        stringFlux.doOnNext(s -> {
                    System.out.print(s);
                    stringBuilder.append(s);
                })
                .doOnComplete(() -> System.out.println("\n完成！"))
                .collectList()
                .block(); // 阻塞等待完成
        Assert.notBlank(stringBuilder);
    }
}