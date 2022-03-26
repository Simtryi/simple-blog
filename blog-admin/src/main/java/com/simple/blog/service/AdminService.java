package com.simple.blog.service;

import com.simple.blog.entity.User;

/**
 * 后台管理 Service
 */
public interface AdminService {

    /**
     * 注册
     */
    User register(User user);

    /**
     * 登录
     */
    String login(String username, String password);

    /**
     * 刷新 token
     * @param authorization 请求头中 Authorization 的值
     */
    String refreshToken(String authorization);

    /**
     * 更新密码
     */
    void updatePassword(String username, String oldPassword, String newPassword);

}
