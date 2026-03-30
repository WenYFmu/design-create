package com.wyf.designcreate.ai.core.aiserver;

import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiCodeServiceFacadeTest {

    @Resource
    private AiCodeServiceFacade aiCodeServiceFacade;

    @Test
    void generateCodeAsSave() {
        String generated = aiCodeServiceFacade.generateCodeAsSave("制作一个个人简历介绍页面", CodeTypeEnum.HTML, 123L);
        System.out.println(generated);
        Assertions.assertNotNull(generated);
    }

    @Test
    void generateCodeAsSaveMultiFile() {
        String generated = aiCodeServiceFacade.generateCodeAsSave("制作一个登录页面", CodeTypeEnum.MULTI_FILE, 12345L);
        System.out.println(generated);
        Assertions.assertNotNull(generated);
    }
}