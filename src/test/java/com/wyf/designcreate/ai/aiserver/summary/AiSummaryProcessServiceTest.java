package com.wyf.designcreate.ai.aiserver.summary;

import cn.hutool.core.lang.Assert;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiSummaryProcessServiceTest {

    @Resource
    private AiSummaryProcessService aiSummaryProcessService;

    @Test
    void extractSummary() {
        LinkedList<ChatMessage> messages = new LinkedList<>();
        messages.add(UserMessage.from("## 根据类型生成【HTML】\n" +
                "\n" +
                "- 通过系统提示词指定生成内容为HTML或HTML、Js、css【枚举类区分】\n" +
                "- 处理两种输出情况、解析并返回【策略模式】【可扩展多种】\n" +
                "- 将两种情况保存到本地【模板方法】【可扩展多种】\n" +
                "- 选择策略或者选择方法的方法可采用选择路由【switch-case（不太符合开闭原则修改了较为核心的业务代码）、**工厂+注册表**Map、等方法】"));
        messages.add(UserMessage.from("## vue工程项目/PPT项目\n" +
                "\n" +
                "> 在开发一个功能的时候，先有一个基本的东西，再向上开发 \n" +
                "\n" +
                "想要让ai实现多个不同的文件的开发，很难通过一个流的模板解析器进行解析，改为让ai自己区分，调用工具进行文件开发【在chatmodel构建中添加】。\n" +
                "\n" +
                "要将工具调用和工具调用结结果拿到手，需要通过tokenstream的事件处理来标准化给前端展示"));
        String s = aiSummaryProcessService.extractSummary(messages, "100");
        System.out.println(s);
        Assert.notBlank(s);
    }
}