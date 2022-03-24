package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;

/**
 * 资源：后台接口
 */
public class Resource extends BaseEntity {

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源 URL
     */
    private String url;

    /**
     * 描述
     */
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
