package com.wyf.designcreate.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 会话消息反馈表
 * @TableName message_feedback
 */
@TableName(value ="message_feedback")
@Data
public class MessageFeedback {
    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 关联的消息ID
     */
    private Long messageId;

    /**
     * 会话ID
     */
    private Long appId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 反馈值 1：点赞 -1：点踩
     */
    private Integer vote;

    /**
     * 反馈原因
     */
    private String reason;

    /**
     * 补充说明
     */
    private String comment;

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