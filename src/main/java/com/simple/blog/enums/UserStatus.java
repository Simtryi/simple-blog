package com.simple.blog.enums;

import lombok.Getter;

/**
 * 用户状态
 */
public enum UserStatus {

    OK("正常"),
    DISABLED("禁用"),
    ;

    @Getter
    private String description;

    UserStatus(String description) {
        this.description = description;
    }

}
