package com.wyf.designcreate.model.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * 应用
 */
@Data
public class AppVO {
    /**
     * id
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
     * 代码生成类型（枚举）
     */
    private String codeGenType;

    /**
     * 部署标识
     */
    private String deployKey;

    /**
     * 部署时间
     */
    private Date deployedTime;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 创建用户VO
     */
    private UserVO userVO;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 创建时间
     */
    private Date createTime;

}