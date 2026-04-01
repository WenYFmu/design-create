package com.wyf.designcreate.ai.core.aiserver.codegen;

import com.wyf.designcreate.ai.core.aiserver.promptsum.AiPromptProcessService;
import com.wyf.designcreate.ai.core.aiserver.promptsum.AiPromptProcessServiceFactory;
import com.wyf.designcreate.ai.core.handler.AiCodeGeneratorStreamHandler;
import com.wyf.designcreate.ai.core.parser.CodeParserExecutor;
import com.wyf.designcreate.ai.core.saver.SaveCodeFileTemplateExecutor;
import com.wyf.designcreate.ai.model.HtmlResult;
import com.wyf.designcreate.ai.model.MultiFileResult;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.exception.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

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

    @Resource
    private AiCodeGeneratorStreamHandler aiCodeGeneratorStreamHandler;

    @Resource
    private AiPromptProcessServiceFactory aiPromptProcessServiceFactory;

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
    /**
     * 生成代码 （流式）
     *
     * @param userMessage  用户消息
     * @param codeTypeEnum 代码类型枚举
     * @return 生成的代码结果
     */
    public Flux<String> generateCodeAsSaveStream(String userMessage, CodeTypeEnum codeTypeEnum, Long appId) {
        AiCodeGeneratorService aiCodeGeneratorService = aiCodeGeneratorServiceFactory.aiCodeGeneratorService();
        return switch (codeTypeEnum) {
            case HTML -> {
                Flux<String> content = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
                yield aiCodeGeneratorStreamHandler.streamHandleAsSave(content, codeTypeEnum, appId);
            }
            case MULTI_FILE -> {
                Flux<String> content = aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
                yield aiCodeGeneratorStreamHandler.streamHandleAsSave(content, codeTypeEnum, appId);

            }
            default -> throw new BusinessException(ErrorCode.OPERATION_ERROR, "不支持的应用类型");
        };
    }

    /**
     * 从初始提示词中提取应用名称
     *
     * @param initPrompt 初始提示词
     * @return 应用名称
     */
    public String extractAppName(String initPrompt) {
        AiPromptProcessService aiPromptProcessService = aiPromptProcessServiceFactory.aiPromptProcessService();
        return aiPromptProcessService.extractAppName(initPrompt);
    }
}
