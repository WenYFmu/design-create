package com.wyf.designcreate.ai.core.parser;

import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.exception.BusinessException;
import org.springframework.stereotype.Component;

/**
 * 代码解析执行器
 * 根据代码生成类型执行相应的解析逻辑
 */
@Component
public class CodeParserExecutor {

    /**
     * 执行代码解析
     *
     * @param contentCode 代码内容
     * @param codeGenType 代码生成类型
     * @return 解析结果（HtmlCodeResult 或 MultiFileCodeResult）
     */
    public Object executorCodeParser(String contentCode, CodeTypeEnum codeGenType) {
        return switch (codeGenType) {
            case HTML -> new HtmlCodeParser().parse(contentCode);
            case MULTI_FILE -> new MultiFileCodeParser().parse(contentCode);
            //todo ppt 解析
            default -> throw new BusinessException(ErrorCode.OPERATION_ERROR, "不支持的应用类型");
        };
    }


}

