package com.wyf.designcreate.model.dto.app;

import lombok.Data;

@Data
public class UpdateAppRequest {

    /**
     * 应用id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用封面
     */
    private String cover;

    /**
     * 应用优先级
     */
    private int priority;
}
