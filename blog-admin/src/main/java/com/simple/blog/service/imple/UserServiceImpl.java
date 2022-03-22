package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.entity.User;
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
    public int create(User user) {
        user.setCreatedAt(new Date());
        return userMapper.insert(user);
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

        User userDB = userMapper.selectById(user.getId());
        if (null == userDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + user.getId() + "对应的用户不存在");
        }

        user.setUpdatedAt(new Date());
        return userMapper.update(user);
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
