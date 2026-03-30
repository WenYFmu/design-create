package com.wyf.designcreate.ai.aiserver;

import com.wyf.designcreate.ai.core.aiserver.AiCodeGeneratorService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiCodeGeneratorServiceTest {


    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;
    @Test
    void generateHtmlCode() {
        String generated = aiCodeGeneratorService.generateHtmlCode("我要一个个人简历页面，不超过30行，并给我讲解一下");
        System.out.println(generated);
        Assertions.assertNotNull(generated);
    }

    @Test
    void generateMultiFileCode() {
        String generated = aiCodeGeneratorService.generateMultiFileCode("我要一个个人简历页面，不超过30行");
        System.out.println(generated);
        Assertions.assertNotNull(generated);
    }
}