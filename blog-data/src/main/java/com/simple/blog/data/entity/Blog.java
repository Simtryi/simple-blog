package com.simple.blog.data.entity;

import com.simple.blog.data.base.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 博客
 */
public class Blog extends BaseEntity {

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 摘要
     */
    @NotBlank(message = "摘要不能为空")
    private String description;

    /**
     * 封面
     */
    private String cover;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 阅读量
     */
    private Long view;

    /**
     * 是否发布
     */
    private Boolean published = false;

    /**
     * 是否允许评论
     */
    private Boolean commented = false;

    /**
     * 分类Id
     */
    private Long categoryId;

    /**
     * 用户Id
     */
    private Long userId;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getCommented() {
        return commented;
    }

    public void setCommented(Boolean commented) {
        this.commented = commented;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
