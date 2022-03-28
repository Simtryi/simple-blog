package com.simple.blog.service.impl;

import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.common.util.StringUtil;
import com.simple.blog.entity.User;
import com.simple.blog.enums.UserStatus;
import com.simple.blog.mapper.UserMapper;
import com.simple.blog.security.util.JWTUtil;
import com.simple.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 后台管理 Service 实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        if (null == user.getUsername()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "用户名不能为空");
        }

        User userDB = userMapper.selectByUsername(user.getUsername());
        if (null != userDB) {
            Asserts.fail(ResultCode.BAD_REQUEST, "用户名已存在");
        }

        //  对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setCreatedAt(new Date());
        user.setStatus(UserStatus.OK);
        userMapper.insert(user);

        return user;
    }

    @Override
    public String login(String username, String password) {
        String token = null;

        try {
            User user = userMapper.selectByUsername(username);
            if (null == user) {
                Asserts.fail(ResultCode.LOGIN, "用户不存在");
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                Asserts.fail(ResultCode.LOGIN, "密码错误");
            }
            if (!user.getStatus().equals(UserStatus.OK)) {
                Asserts.fail(ResultCode.LOGIN, "账号已被禁用");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = JWTUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            Asserts.fail(ResultCode.LOGIN, e.getMessage());
        }

        return token;
    }

    @Override
    public String refreshToken(String authorization) {
        return JWTUtil.refreshToken(authorization);
    }

    @Override
    public void updatePassword(String username, String oldPassword, String newPassword) {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(oldPassword) || StringUtil.isEmpty(newPassword)) {
            Asserts.fail(ResultCode.BAD_REQUEST, "参数不合法");
        }

        User user = userMapper.selectByUsername(username);
        if (null == user) {
            Asserts.fail(ResultCode.BAD_REQUEST, "用户不存在");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            Asserts.fail(ResultCode.BAD_REQUEST, "原密码错误");
        }

        if (oldPassword.equals(newPassword)) {
            Asserts.fail(ResultCode.BAD_REQUEST, "新密码不能与原密码重复");
        }

        String encodePassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodePassword);
        user.setUpdatedAt(new Date());
        userMapper.update(user);
    }

}
