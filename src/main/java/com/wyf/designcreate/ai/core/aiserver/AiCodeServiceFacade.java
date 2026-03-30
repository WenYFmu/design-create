package com.wyf.designcreate.ai.core.aiserver;

import com.wyf.designcreate.ai.core.parser.CodeParserExecutor;
import com.wyf.designcreate.ai.core.saver.SaveCodeFileTemplateExecutor;
import com.wyf.designcreate.ai.model.HtmlResult;
import com.wyf.designcreate.ai.model.MultiFileResult;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.exception.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * ai服务门面
 */
@Component
public class AiCodeServiceFacade {

    @Resource
    private AiCodeGeneratorServiceFactory aiCodeGeneratorServiceFactory;

    @Resource
    private CodeParserExecutor codeParserExecutor;

    @Resource
    private SaveCodeFileTemplateExecutor saveCodeFileTemplateExecutor;

    /**
     * 生成代码
     *
     * @param userMessage  用户消息
     * @param codeTypeEnum 代码类型枚举
     * @return 生成的代码结果
     */
    public String generateCodeAsSave(String userMessage, CodeTypeEnum codeTypeEnum, Long appId) {
        AiCodeGeneratorService aiCodeGeneratorService = aiCodeGeneratorServiceFactory.aiCodeGeneratorService();
        return switch (codeTypeEnum) {
            case HTML -> {
                String content = aiCodeGeneratorService.generateHtmlCode(userMessage);
                HtmlResult parseResult = (HtmlResult) codeParserExecutor.executorCodeParser(content, codeTypeEnum);
                saveCodeFileTemplateExecutor.saveCodeFile(parseResult, appId, codeTypeEnum);
                yield content;
            }
            case MULTI_FILE -> {
                String content = aiCodeGeneratorService.generateMultiFileCode(userMessage);
                MultiFileResult parseResult = (MultiFileResult) codeParserExecutor.executorCodeParser(content, codeTypeEnum);
                saveCodeFileTemplateExecutor.saveCodeFile(parseResult, appId, codeTypeEnum);
                yield content;
            }
            default -> throw new BusinessException(ErrorCode.OPERATION_ERROR, "不支持的应用类型");
        };
    }

}
