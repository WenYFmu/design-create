package com.wyf.designcreate.ai.core.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.wyf.designcreate.ai.core.parser.CodeParserExecutor;
import com.wyf.designcreate.ai.core.saver.SaveCodeFileTemplateExecutor;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import com.wyf.designcreate.ai.tokenstream.PartialResponseMessage;
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
        StringBuilder contentBuilder = new StringBuilder();
        return stream
                .doOnNext(contentBuilder::append)
                .map(content -> JSONUtil.toJsonStr(new PartialResponseMessage(content)))
                .doOnComplete(() -> {
                    //收到全部响应后解析
                    Object result = codeParserExecutor.executorCodeParser(contentBuilder.toString(), codeTypeEnum);
                    //保存文件
                    saveCodeFileTemplateExecutor.saveCodeFile(result, appId, codeTypeEnum);
                });
    }
}
