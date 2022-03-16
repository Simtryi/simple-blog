package com.simple.blog.entity;

import com.simple.blog.base.BaseEntity;
import com.simple.blog.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态，正常：OK 禁用：DISABLED
     */
    private UserStatus status;

}
