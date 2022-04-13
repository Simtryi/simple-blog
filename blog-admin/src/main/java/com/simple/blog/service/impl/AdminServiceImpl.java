package com.simple.blog.service.impl;

import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.ApiException;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.common.util.StringUtil;
import com.simple.blog.entity.User;
import com.simple.blog.enums.UserStatus;
import com.simple.blog.mapper.UserMapper;
import com.simple.blog.security.util.JwtUtil;
import com.simple.blog.service.AdminService;
import com.simple.blog.service.UserCacheService;
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
    private UserCacheService userCacheService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        User userDB = userMapper.findByUsername(user.getUsername());
        if (null != userDB) {
            Asserts.fail(ResultCode.BAD_REQUEST, "用户名已存在");
        }

        //  对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setCreatedAt(new Date());
        user.setStatus(UserStatus.OK);
        userMapper.save(user);

        return user;
    }

    @Override
    public String login(String username, String password) {
        String token;

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail(ResultCode.LOGIN, "密码错误");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail(ResultCode.LOGIN, "账号已被禁用");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = JwtUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new ApiException(ResultCode.LOGIN, e.getMessage());
        }

        return token;
    }

    @Override
    public String refreshToken(String authorization) {
        return JwtUtil.refreshToken(authorization);
    }

    @Override
    public void updatePassword(String username, String oldPassword, String newPassword) {
        User user = userMapper.findByUsername(username);
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

        //  由于用户密码更新，删除用户缓存
        userCacheService.delUserCache(user.getId());
    }

}
