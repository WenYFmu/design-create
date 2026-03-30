package com.wyf.designcreate.ai.core.saver;

import cn.hutool.core.util.StrUtil;
import com.wyf.designcreate.ai.model.HtmlResult;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveHtmlCodeFile extends SaveCodeFileTemplate<HtmlResult>{
    @Override
    protected CodeTypeEnum getCodeTypeEnum() {
        return CodeTypeEnum.HTML;
    }

    @Override
    protected void saveFiles(HtmlResult result, String filePath) {
        writeToFile(filePath, "index.html", result.getHtml(), StrUtil.isNotBlank(result.getHtml()));
    }

    @Override
    protected boolean validateInput(HtmlResult result) {
        boolean res = super.validateInput(result);
        if(res && StrUtil.isBlank(result.getHtml())){
            log.info("HTML不存在，可能在解释代码");
        }
        return res;
    }
}
