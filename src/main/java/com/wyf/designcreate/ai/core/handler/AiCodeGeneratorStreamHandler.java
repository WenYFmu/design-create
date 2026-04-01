package com.wyf.designcreate.ai.core.handler;

import com.wyf.designcreate.ai.core.parser.CodeParserExecutor;
import com.wyf.designcreate.ai.core.saver.SaveCodeFileTemplateExecutor;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class AiCodeGeneratorStreamHandler {

    @Resource
    private CodeParserExecutor codeParserExecutor;

    @Resource
    private SaveCodeFileTemplateExecutor saveCodeFileTemplateExecutor;

    public Flux<String> streamHandleAsSave(Flux<String> stream, CodeTypeEnum codeTypeEnum, Long appId) {
        StringBuilder content = new StringBuilder();
        return stream.doOnNext(content::append)
                .doOnComplete(() -> {
                    //收到全部响应后解析
                    Object result = codeParserExecutor.executorCodeParser(content.toString(), codeTypeEnum);
                    //保存文件
                    saveCodeFileTemplateExecutor.saveCodeFile(result, appId, codeTypeEnum);
                });
    }
}
