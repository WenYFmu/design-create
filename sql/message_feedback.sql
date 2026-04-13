CREATE TABLE `message_feedback`
(
    `id`         bigint                                  NOT NULL COMMENT '主键ID',
    `messageId`  bigint                                  NOT NULL COMMENT '关联的消息ID',
    `appId`      bigint                                  NOT NULL COMMENT '会话ID',
    `userId`     bigint                                  NOT NULL COMMENT '用户ID',
    `vote`       tinyint(1)                              NOT NULL COMMENT '反馈值 1：点赞 -1：点踩',
    `reason`     varchar(255)  DEFAULT NULL COMMENT '反馈原因',
    `comment`    varchar(1024) DEFAULT NULL COMMENT '补充说明',
    `createTime` datetime      default CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    `updateTime` datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted`  tinyint                                 NOT NULL DEFAULT '0' COMMENT '是否删除 0：正常 1：删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_msg_user` (`messageId`, `userId`),
    INDEX `idx_appId` (`appId`),
    INDEX `idx_user_id` (`userId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='会话消息反馈表';
-- id自增
ALTER TABLE `message_feedback`
    MODIFY COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID';