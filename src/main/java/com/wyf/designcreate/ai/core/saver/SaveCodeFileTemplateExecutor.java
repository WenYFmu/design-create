package com.wyf.designcreate.ai.core.saver;

import com.wyf.designcreate.ai.model.HtmlResult;
import com.wyf.designcreate.ai.model.MultiFileResult;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class SaveCodeFileTemplateExecutor {

    private static final SaveHtmlCodeFile saveCodeFileTemplate = new SaveHtmlCodeFile();

    private static final SaveMultiFileCodeFile saveMultiFileCodeFile = new SaveMultiFileCodeFile();

    public void saveCodeFile(Object result, Long appId, CodeTypeEnum codeType) {
        switch (codeType){
            case HTML:
                saveCodeFileTemplate.saveCodeFile((HtmlResult) result, appId);
                break;
            case MULTI_FILE:
                saveMultiFileCodeFile.saveCodeFile((MultiFileResult) result, appId);
                break;
            default:
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "不支持的应用类型");
        }
    }

}
