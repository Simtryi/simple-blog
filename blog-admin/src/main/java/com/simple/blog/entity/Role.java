package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;
import com.simple.blog.enums.RoleStatus;

import javax.validation.constraints.NotBlank;

/**
 * 角色
 */
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 用户数量
     */
    private Integer count;

    /**
     * 状态，正常：OK 禁用：DISABLED
     */
    private RoleStatus status = RoleStatus.OK;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public RoleStatus getStatus() {
        return status;
    }

    public void setStatus(RoleStatus status) {
        this.status = status;
    }

}
