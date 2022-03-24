package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;

/**
 * 角色资源关系
 */
public class RoleResourceRelation extends BaseEntity {

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 资源Id
     */
    private Long resourceId;



    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

}
