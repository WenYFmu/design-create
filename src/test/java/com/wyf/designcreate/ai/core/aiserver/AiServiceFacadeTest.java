package com.wyf.designcreate.ai.core.aiserver;

import cn.hutool.core.lang.Assert;
import com.wyf.designcreate.ai.aiserver.AiServiceFacade;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
class AiServiceFacadeTest {

    @Resource
    private AiServiceFacade aiServiceFacade;

    @Test
    void generateCodeAsSave() {
        String generated = aiServiceFacade.generateCodeAsSave("制作一个个人简历介绍页面", CodeTypeEnum.HTML, 123L);
        System.out.println(generated);
        Assertions.assertNotNull(generated);
    }

    @Test
    void generateCodeAsSaveMultiFile() {
        String generated = aiServiceFacade.generateCodeAsSave("制作一个登录页面", CodeTypeEnum.MULTI_FILE, 12345L);
        System.out.println(generated);
        Assertions.assertNotNull(generated);
    }

    @Test
    void generateCodeAsSaveMultiFileStream() {
        Flux<String> generated = aiServiceFacade.generateCodeAsSaveStream("制作一个登录注册页面", CodeTypeEnum.MULTI_FILE, 2039247184735330306L);
        StringBuilder stringBuilder = new StringBuilder();
        generated.doOnNext(stringBuilder::append).collectList().block();
        Assert.notBlank(stringBuilder);
    }
}