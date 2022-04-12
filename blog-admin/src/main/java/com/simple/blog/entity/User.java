package com.simple.blog.entity;

import com.simple.blog.common.base.BaseEntity;
import com.simple.blog.enums.UserStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户
 */
public class User extends BaseEntity implements Serializable {

    /**
     * 用户名，全局唯一
     */
    @NotEmpty
    private String username;

    /**
     * 密码
     */
    @NotEmpty
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    @Email
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态，正常：OK 禁用：DISABLED
     */
    private UserStatus status = UserStatus.OK;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

}
