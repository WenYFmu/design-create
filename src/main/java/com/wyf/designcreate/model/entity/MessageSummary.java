package com.wyf.designcreate.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 会话摘要表（与消息表分离存储）
 * @TableName message_summary
 */
@TableName(value ="message_summary")
@Data
public class MessageSummary {
    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 会话ID
     */
    private Long appId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 摘要最后消息ID
     */
    private Long lastMessageId;

    /**
     * 会话摘要内容
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