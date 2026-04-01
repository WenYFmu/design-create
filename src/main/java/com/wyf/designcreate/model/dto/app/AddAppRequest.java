package com.wyf.designcreate.model.dto.app;

import lombok.Data;

@Data
public class AddAppRequest {
    /**
     * 应用名称
     */
    private String appName;


    /**
     * 初始化提示语
     */
    private String initPrompt;

}
