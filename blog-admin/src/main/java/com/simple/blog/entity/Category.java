package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;

/**
 * 分类
 */
public class Category extends BaseEntity {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类图标
     */
    private String icon;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
