package com.simple.blog.base;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createdAt = new Date();

    /**
     * 更新时间
     */
    private Date updatedAt = new Date();

}
