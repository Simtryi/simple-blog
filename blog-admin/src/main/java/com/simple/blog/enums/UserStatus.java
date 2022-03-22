package com.simple.blog.enums;

/**
 * 用户状态
 */
public enum UserStatus {

    OK("正常"),
    DISABLED("禁用"),
    ;

    private String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
