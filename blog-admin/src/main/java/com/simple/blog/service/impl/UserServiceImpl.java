package com.simple.blog.service.impl;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.entity.User;
import com.simple.blog.mapper.UserMapper;
import com.simple.blog.service.AdminService;
import com.simple.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户管理 Service 实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminService adminService;

    @Override
    public User create(User user) {
        return adminService.register(user);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int update(User user) {
        if (null == user.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填！");
        }

        if (null == user.getUsername()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "用户名不能为空");
        }

        User userDB = userMapper.selectById(user.getId());
        if (null == userDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + user.getId() + "对应的用户不存在");
        }

        User userDB2 = userMapper.selectByUsername(user.getUsername());
        if (null != userDB2) {
            Asserts.fail(ResultCode.BAD_REQUEST, "用户名已存在");
        }

        user.setUpdatedAt(new Date());
        return userMapper.update(user);
    }

    @Override
    public User detail(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public Page<User> list(int pageNum, int pageSize, String username, String nickname, String email) {
        return userMapper.page(pageNum, pageSize, username, nickname, email);
    }

}
