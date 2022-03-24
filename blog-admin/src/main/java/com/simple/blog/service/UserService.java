package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.entity.User;

/**
 * 用户管理 Service
 */
public interface UserService {

    /**
     * 添加用户
     */
    int create(User user);

    /**
     * 删除用户
     */
    int delete(Long id);

    /**
     * 修改用户
     */
    int update(User user);

    /**
     * 查询用户
     */
    User detail(Long id);

    /**
     * 根据用户名查找用户
     */
    User getUserByUsername(String username);

    /**
     * 分页查询用户
     */
    Page<User> list(int pageNum, int pageSize, String username, String nickname, String email);

}
