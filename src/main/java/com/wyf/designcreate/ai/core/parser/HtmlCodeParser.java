package com.wyf.designcreate.ai.core.parser;

import cn.hutool.core.util.StrUtil;
import com.wyf.designcreate.ai.model.HtmlResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML 单文件代码解析器
 */
public class HtmlCodeParser implements CodeParser<HtmlResult>{

    //正则匹配
    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);

    @Override
    public HtmlResult parse(String codeContent) {
        HtmlResult htmlResult = new HtmlResult();
        String html = extractHtmlCode(codeContent);
        if(StrUtil.isNotBlank( html)){
            htmlResult.setHtml(html);
        }else {
            htmlResult.setDescription(codeContent);
        }
        return htmlResult;
    }

    /**
     * 提取HTML代码内容
     *
     * @param content 原始内容
     * @return HTML代码
     */
    private String extractHtmlCode(String content) {
        Matcher matcher = HTML_CODE_PATTERN.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
