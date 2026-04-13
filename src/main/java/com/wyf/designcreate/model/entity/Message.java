package com.wyf.designcreate.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 应用会话消息记录表
 * @TableName message
 */
@TableName(value ="message")
@Data
public class Message {
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 消息类型：system/user/assistant
     */
    private String type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0：正常 1：删除
     */
    private Integer isDeleted;
}