package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.entity.User;

public interface UserService {

    /**
     * 添加用户
     */
    User create(User user);

    /**
     * 删除用户
     */
    void delete(Long id);

    /**
     * 修改用户
     */
    User update(User user);

    /**
     * 查询用户
     */
    User detail(Long id);

    /**
     * 分页查询用户
     */
    Page<User> list(int pageNum, int pageSize, String username, String nickname, String email);

}
