package com.simple.blog.Config;

import com.simple.blog.entity.AdminUserDetails;
import com.simple.blog.entity.User;
import com.simple.blog.security.config.SecurityConfig;
import com.simple.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 后台管理安全配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends SecurityConfig {

    @Autowired
    private UserService userService;

    /**
     * 获取登录用户信息
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.getUserByUsername(username);
            if (null != user) {
                return new AdminUserDetails(user);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

}
