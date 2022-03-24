package com.simple.blog.service.imple;

import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.entity.User;
import com.simple.blog.enums.UserStatus;
import com.simple.blog.mapper.UserMapper;
import com.simple.blog.security.util.JWTUtil;
import com.simple.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

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
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = JWTUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            //  todo
            //  LOGGER.warn("登录异常：{}", e.getMessage());
        }
        return token;
    }

}
