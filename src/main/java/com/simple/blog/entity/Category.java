package com.simple.blog.entity;

import com.simple.blog.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类图标
     */
    private String icon;

}
