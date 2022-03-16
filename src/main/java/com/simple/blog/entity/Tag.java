package com.simple.blog.entity;

import com.simple.blog.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {

    /**
     * 标签名称
     */
    private String name;

}
