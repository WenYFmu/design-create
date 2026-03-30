package com.wyf.designcreate.ai.model;

import lombok.Data;

@Data
public class MultiFileResult {

    /**
     * 上下文中解析出的html代码
     */
    private String html;

    private String css;

    private String js;

    /**
     * 代码的描述
     */
    private String description;
}
