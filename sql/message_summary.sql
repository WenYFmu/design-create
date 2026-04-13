CREATE TABLE `message_summary`
(
    `id`            bigint                             NOT NULL COMMENT '主键ID',
    `appId`         bigint                             NOT NULL COMMENT '会话ID',
    `userId`        bigint                             NOT NULL COMMENT '用户ID',
    `lastMessageId` bigint                             NOT NULL COMMENT '摘要最后消息ID',
    `content`       text                               NOT NULL COMMENT '会话摘要内容',
    `createTime`    datetime default CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    `updateTime`    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted`     tinyint  DEFAULT '0' COMMENT '是否删除 0：正常 1：删除',
    PRIMARY KEY (`id`),
    INDEX `idx_appId` (`appId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='会话摘要表（与消息表分离存储）';
-- id自增
ALTER TABLE `message_summary`
    MODIFY COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID';
