package com.wyf.designcreate.ai.core.parser;

import cn.hutool.core.util.StrUtil;
import com.wyf.designcreate.ai.model.MultiFileResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML 多文件代码解析器
 */
public class MultiFileCodeParser implements CodeParser<MultiFileResult> {

    //正则匹配
    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);

    private static final Pattern CSS_CODE_PATTERN = Pattern.compile("```css\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);

    private static final Pattern JS_CODE_PATTERN = Pattern.compile("```(?:js|javascript)\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);

    @Override
    public MultiFileResult parse(String codeContent) {
        MultiFileResult multiFileResult = new MultiFileResult();
        String html = extractMultiCode(codeContent, HTML_CODE_PATTERN);
        String css = extractMultiCode(codeContent, CSS_CODE_PATTERN);
        String js = extractMultiCode(codeContent, JS_CODE_PATTERN);
        if (StrUtil.isNotBlank(html)) {
            multiFileResult.setHtml(html);
        }
        if (StrUtil.isNotBlank(css)) {
            multiFileResult.setCss(css);
        }
        if (StrUtil.isNotBlank(js)) {
            multiFileResult.setJs(js);
        }
        return multiFileResult;
    }

    /**
     * 提取上下文代码内容
     *
     * @param content 原始内容
     * @return 代码
     */
    private String extractMultiCode(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
