package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;

/**
 * 博客标签关系
 */
public class BlogTagRelation extends BaseEntity {

    /**
     * 博客Id
     */
    private Long blogId;

    /**
     * 标签Id
     */
    private Long tagId;



    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

}
