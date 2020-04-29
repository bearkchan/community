CREATE TABLE `community`.`co_comment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `parent_id` bigint(0) NOT NULL COMMENT '父类id',
  `type` int(11) NOT NULL COMMENT '类型',
  `commentator` int(11) NOT NULL COMMENT '评论人id',
  `gmt_create` bigint(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(0) NOT NULL COMMENT '修改时间',
  `like_count` int(11) NULL DEFAULT 0 COMMENT '点赞数',
  `content` text NULL COMMENT '评论内容',
  PRIMARY KEY (`id`)
);