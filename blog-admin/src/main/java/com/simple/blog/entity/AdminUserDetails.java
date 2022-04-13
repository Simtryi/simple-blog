package com.simple.blog.entity;

import com.simple.blog.data.entity.Resource;
import com.simple.blog.data.entity.User;
import com.simple.blog.data.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Spring Security 自定义用户信息
 */
public class AdminUserDetails implements UserDetails {

    /**
     * 后台用户
     */
    private User user;

    /**
     * 用户可访问的资源
     */
    private List<Resource> resourceList;

    public AdminUserDetails(User user, List<Resource> resourceList) {
        this.user = user;
        this.resourceList = resourceList;
    }

    /**
     * 获取用户的权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return resourceList.stream()
                .map(resource -> new SimpleGrantedAuthority(resource.getId() + ":" + resource.getName()))
                .collect(Collectors.toList());
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
