package com.simple.blog.entity;

import com.simple.blog.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博客
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Blog extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String cover;

    /**
     * MD文件源码
     */
    private String mdContent;

    /**
     * HTML文件源码
     */
    private String htmlContent;

    /**
     * 阅读量
     */
    private Long view;

    /**
     * 是否发布
     */
    private Boolean isPublish;

    /**
     * 是否允许评论
     */
    private Boolean isComment;

    /**
     * 分类Id
     */
    private Long categoryId;

    /**
     * 用户Id
     */
    private Long userId;

}
