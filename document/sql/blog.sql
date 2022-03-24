create database `simple_blog` default character set utf8;

use `simple_blog`;

-- ----------------------------
-- Table structure for user
-- ----------------------------
drop table if exists `user`;
create table `user` (
    `id` bigint(20) not null auto_increment comment '主键',
    `created_at` datetime not null default current_timestamp comment '创建时间',
    `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `username` varchar(100) default null comment '用户名',
    `password` varchar(100) default null comment '密码',
    `nickname` varchar(100) default null comment '昵称',
    `email` varchar(100) default null comment '邮箱',
    `avatar` varchar(256) default null comment '头像',
    `status` varchar(100) default 'OK' comment '状态，正常：OK 禁用：DISABLED',
    primary key (`id`)
) engine=InnoDB default charset=utf8 comment '用户表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
drop table if exists `role`;
create table `role` (
    `id` bigint(20) not null auto_increment comment '主键',
    `created_at` datetime not null default current_timestamp comment '创建时间',
    `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `name` varchar(100) default null comment '角色名称',
    `description` varchar(512) default null comment '描述',
    `count` int default null comment '用户数量',
    `status` varchar(100) default 'OK' comment '状态，正常：OK 禁用：DISABLED',
    primary key (`id`)
) engine=InnoDB default charset=utf8 comment '角色表';

-- ----------------------------
-- Table structure for user_role_relation
-- ----------------------------
drop table if exists `user_role_relation`;
create table `user_role_relation` (
    `id` bigint(20) not null auto_increment comment '主键',
    `created_at` datetime not null default current_timestamp comment '创建时间',
    `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `user_id` bigint(20) default null comment '用户Id',
    `role_id` bigint(20) default null comment '角色Id',
    primary key (`id`),
    key `idx_user_id` (`user_id`),
    key `idx_role_id` (`role_id`)
) engine=InnoDB default charset=utf8 comment '用户角色关系表';

-- ----------------------------
-- Table structure for resource
-- ----------------------------
drop table if exists `resource`;
create table `resource` (
    `id` bigint(20) not null auto_increment comment '主键',
    `created_at` datetime not null default current_timestamp comment '创建时间',
    `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `name` varchar(200)  default null comment '资源名称',
    `url` varchar(200) default null comment '资源URL',
    `description` varchar(512) default null comment '描述',
    primary key (`id`)
) engine=InnoDB default charset=utf8 comment '资源表';

-- ----------------------------
-- Table structure for role_resource_relation
-- ----------------------------
drop table if exists `role_resource_relation`;
create table `role_resource_relation` (
    `id` bigint(20) not null auto_increment comment '主键',
    `created_at` datetime not null default current_timestamp comment '创建时间',
    `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `role_id` bigint(20) default null comment '角色Id',
    `resource_id` bigint(20) default null comment '资源Id',
    primary key (`id`),
    key `idx_role_id` (`role_id`),
    key `idx_resource_id` (`resource_id`)
) engine=InnoDB default charset=utf8 comment '角色资源关系表';

-- ----------------------------
-- Table structure for blog
-- ----------------------------
drop table if exists `blog`;
create table `blog` (
    `id` bigint(20) not null auto_increment comment '主键',
    `created_at` datetime not null default current_timestamp comment '创建时间',
    `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `title` varchar(256) default null comment '标题',
    `cover` varchar(256) default null comment '封面',
    `md_content` text comment 'MD文件源码',
    `html_content` text comment 'HTML文件源码',
    `view` bigint(20) not null default '0' comment '阅读量',
    `published` tinyint(1) not null default '0' comment '是否发布，0：不发布 1：发布',
    `commented` tinyint(1) not null default '0' comment '是否允许评论，0：不允许 1：允许',
    `category_id` bigint(20) default null comment '分类Id',
    `user_id` bigint(20) default null comment '用户Id',
    primary key (`id`),
    key `idx_category_id` (`category_id`),
    key `idx_user_id` (`user_id`)
) engine=InnoDB default charset=utf8 comment '博客表';

-- ----------------------------
-- Table structure for category
-- ----------------------------
drop table if exists `category`;
create table `category` (
    `id` bigint(20) not null auto_increment comment '主键',
    `created_at` datetime not null default current_timestamp comment '创建时间',
    `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `name` varchar(100) default null comment '分类名称',
    `icon` varchar(100) default null comment '分类图标',
    primary key (`id`)
) engine=InnoDB default charset=utf8 comment '分类表';

-- ----------------------------
-- Table structure for tag
-- ----------------------------
drop table if exists `tag`;
create table `tag` (
    `id` bigint(20) not null auto_increment comment '主键',
    `created_at` datetime not null default current_timestamp comment '创建时间',
    `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `name` varchar(100) default null comment '标签名称',
    primary key (`id`)
) engine=InnoDB default charset=utf8 comment '标签表';

-- ----------------------------
-- Table structure for blog_tag_relation
-- ----------------------------
drop table if exists `blog_tag_relation`;
create table `blog_tag_relation` (
     `id` bigint(20) not null auto_increment comment '主键',
     `created_at` datetime not null default current_timestamp comment '创建时间',
     `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
     `blog_id` bigint(20) default null comment '博客Id',
     `tag_id` bigint(20) default null comment '标签Id',
     primary key (`id`),
     key `idx_blog_id` (`blog_id`),
     key `idx_tag_id` (`tag_id`)
) engine=InnoDB default charset=utf8 comment '博客标签关系表';

-- ----------------------------
-- Table structure for comment
-- ----------------------------
drop table if exists `comment`;
create table `comment` (
    `id` bigint(20) not null auto_increment comment '主键',
    `created_at` datetime not null default current_timestamp comment '创建时间',
    `updated_at` datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    `content` text comment '评论内容',
    `pid` bigint(20) default null comment '父评论Id',
    `blog_id` bigint(20) default null comment '博客Id',
    `user_id` bigint(20) default null comment '用户Id',
    primary key (`id`),
    key `idx_blog_id` (`blog_id`),
    key `idx_user_id` (`user_id`)
) engine=InnoDB default charset=utf8 comment '评论表';