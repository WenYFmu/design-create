CREATE TABLE `message`
(
    `id`         bigint                             NOT NULL COMMENT '主键ID',
    `appId`      bigint                             NOT NULL COMMENT '应用ID',
    `userId`     bigint                             NOT NULL COMMENT '用户ID',
    `type`       varchar(32)                        NOT NULL COMMENT '消息类型：system/user/assistant',
    `content`    text                               NOT NULL COMMENT '消息内容',
    `createTime` datetime default CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDeleted`  tinyint  DEFAULT '0' COMMENT '是否删除 0：正常 1：删除',
    PRIMARY KEY (`id`),
    INDEX `idx_app_createTime` (`appId`, `createTime`),
    INDEX `idx_appId` (`appId`),
    INDEX `idx_createTime` (`createTime`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='应用会话消息记录表';

-- id自增
ALTER TABLE `message`
    MODIFY COLUMN `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID';