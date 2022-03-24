package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;

/**
 * 用户角色关系
 */
public class UserRoleRelation extends BaseEntity {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 角色Id
     */
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
