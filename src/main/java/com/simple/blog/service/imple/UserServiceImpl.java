package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
import com.simple.blog.entity.User;
import com.simple.blog.mapper.UserMapper;
import com.simple.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User create(User user) {
        //  todo 校验
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
            //  todo 抛出异常
        }

        User userDB = userMapper.selectById(user.getId());
        if (null == userDB) {
            //  todo 抛出异常
        }

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
