package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;

/**
 * 标签
 */
public class Tag extends BaseEntity {

    /**
     * 标签名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
