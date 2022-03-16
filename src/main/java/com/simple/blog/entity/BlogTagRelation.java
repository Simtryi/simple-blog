package com.simple.blog.entity;

import com.simple.blog.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博客标签关系
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogTagRelation extends BaseEntity {

    /**
     * 博客Id
     */
    private Long blogId;

    /**
     * 标签Id
     */
    private Long tagId;

}
