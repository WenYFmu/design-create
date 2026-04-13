package com.wyf.designcreate.ai.core.aiserver;

import cn.hutool.core.lang.Assert;
import com.wyf.designcreate.ai.aiserver.codegen.AiCodeGeneratorService;
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
        Flux<String> stringFlux = aiCodeGeneratorService.generateHtmlCodeStream(1L,"生成一个HTML页面，内容是“Hello World”");
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
        Flux<String> stringFlux = aiCodeGeneratorService.generateMultiFileCodeStream(1L, "根据我的名字展示html大字不超过10行代码");
        StringBuilder stringBuilder = new StringBuilder();
        stringFlux.doOnNext(s -> {
                    System.out.print(s);
                    stringBuilder.append(s);
                })
                .doOnComplete(() -> System.out.println("\n完成！"))
                .collectList()
                .block(); // 阻塞等待完成
        Assert.notBlank(stringBuilder);

        Flux<String> stringFlux1 = aiCodeGeneratorService.generateMultiFileCodeStream(1L, "名字改为我的世界");
        StringBuilder stringBuilder1 = new StringBuilder();
        stringFlux1.doOnNext(s -> {
                    System.out.print(s);
                    stringBuilder1.append(s);
                })
                .doOnComplete(() -> System.out.println("\n完成！"))
                .collectList()
                .block(); // 阻塞等待完成
        Assert.notBlank(stringBuilder1);
    }
}