package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;

/**
 * 用户资源关系
 */
public class UserResourceRelation extends BaseEntity {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 资源Id
     */
    private Long resourceId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

}
