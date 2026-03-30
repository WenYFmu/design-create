package com.wyf.designcreate.ai.core.saver;

import cn.hutool.core.util.StrUtil;
import com.wyf.designcreate.ai.model.HtmlResult;
import com.wyf.designcreate.ai.model.MultiFileResult;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveMultiFileCodeFile extends SaveCodeFileTemplate<MultiFileResult>{
    @Override
    protected CodeTypeEnum getCodeTypeEnum() {
        return CodeTypeEnum.MULTI_FILE;
    }

    @Override
    protected void saveFiles(MultiFileResult result, String filePath) {
        String html = result.getHtml();
        writeToFile(filePath, "index.html", html, StrUtil.isNotBlank(html));
        String css = result.getCss();
        writeToFile(filePath, "style.css", css, StrUtil.isNotBlank(css));
        String js = result.getJs();
        writeToFile(filePath, "script.js", js, StrUtil.isNotBlank(js));
    }
    @Override
    protected boolean validateInput(MultiFileResult result) {
        boolean res = super.validateInput(result);
        if(res && StrUtil.isBlank(result.getHtml())){
            log.info("HTML不存在，可能在解释代码");
        }
        return res;
    }
}
