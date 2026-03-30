package com.wyf.designcreate.ai.model;

import lombok.Data;

@Data
public class HtmlResult {

    /**
     * 上下文中解析出的html代码
     */
    private String html;

    /**
     * html代码的描述
     */
    private String description;
}
