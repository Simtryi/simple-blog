package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;

/**
 * 评论
 */
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
