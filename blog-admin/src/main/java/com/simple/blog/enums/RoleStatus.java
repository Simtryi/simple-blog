package com.simple.blog.enums;

/**
 * 角色状态
 */
public enum RoleStatus {

    OK("正常"),
    DISABLED("禁用"),
    ;

    private String description;

    RoleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
