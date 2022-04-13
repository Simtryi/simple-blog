package com.simple.blog.service;

import com.simple.blog.data.entity.Resource;
import com.simple.blog.data.entity.User;

import java.util.List;

/**
 * 用户缓存管理 Service
 */
public interface UserCacheService {

    /**
     * 获取用户缓存
     */
    User getUserCache(String username);

    /**
     * 设置用户缓存
     */
    void setUserCache(User user);

    /**
     * 删除用户缓存
     */
    void delUserCache(Long userId);

    /**
     * 获取用户资源缓存
     */
    List<Resource> getResourceCache(Long userId);

    /**
     * 设置用户资源缓存
     */
    void setResourceCache(Long userId, List<Resource> resourceList);

    /**
     * 当用户信息改变时，删除用户的资源缓存
     */
    void delResourceCacheByUserId(Long userId);

    /**
     * 当角色信息改变时，删除用户的资源缓存
     */
    void delResourceCacheByRoleId(Long roleId);

    /**
     * 当资源信息改变时，删除用户的资源缓存
     */
    void delResourceCacheByResourceId(Long resourceId);

}
