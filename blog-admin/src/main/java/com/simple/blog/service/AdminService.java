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

}
