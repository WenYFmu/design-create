package com.wyf.designcreate.model.dto.app;

import lombok.Data;

@Data
public class AddAppRequest {
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用代码生成类型
     */
    private String codeGenType;

    /**
     * 初始化提示语
     */
    private String initPrompt;

}
