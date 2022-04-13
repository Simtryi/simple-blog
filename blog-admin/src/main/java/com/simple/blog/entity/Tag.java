package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 标签
 */
public class Tag extends BaseEntity {

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
