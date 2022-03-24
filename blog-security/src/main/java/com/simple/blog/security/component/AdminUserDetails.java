package com.simple.blog.security.component;

import com.simple.blog.entity.User;
import com.simple.blog.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security 自定义用户信息
 */
public class AdminUserDetails implements UserDetails {

    /**
     * 后台用户
     */
    private User user;

    public AdminUserDetails(User user) {
        this.user = user;
    }

    /**
     * 获取用户的权限集
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 获取用户的加密密码
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户的用户名
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 判断账户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断账户是否锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 判断凭证是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断用户是否可用
     */
    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(UserStatus.OK);
    }

}
