package com.simple.blog.entity;

import com.simple.blog.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 评论
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论Id
     */
    private Long pid;

    /**
     * 博客Id
     */
    private Long blogId;

    /**
     * 用户Id
     */
    private Long userId;

}
