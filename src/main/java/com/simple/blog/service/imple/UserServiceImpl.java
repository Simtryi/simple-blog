package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
import com.simple.blog.common.ResultCode;
import com.simple.blog.entity.User;
import com.simple.blog.exception.Asserts;
import com.simple.blog.mapper.UserMapper;
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
    UserMapper userMapper;

    @Override
    public User create(User user) {
        user.setCreatedAt(new Date());
        userMapper.insert(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        User user = userMapper.selectById(id);
        if (null != user) {
            userMapper.deleteById(id);
        }
    }

    @Override
    public User update(User user) {
        if (null == user.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填！");
        }

        User userDB = userMapper.selectById(user.getId());
        if (null == userDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + user.getId() + "对应的用户不存在");
        }

        user.setUpdatedAt(new Date());
        userMapper.update(user);
        return user;
    }

    @Override
    public User detail(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public Page<User> list(int pageNum, int pageSize, String username, String nickname, String email) {
        return userMapper.page(pageNum, pageSize, username, nickname, email);
    }

}
