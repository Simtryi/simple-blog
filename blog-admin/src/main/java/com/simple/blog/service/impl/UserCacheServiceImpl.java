package com.simple.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.simple.blog.common.service.RedisService;
import com.simple.blog.entity.Resource;
import com.simple.blog.entity.User;
import com.simple.blog.mapper.UserMapper;
import com.simple.blog.mapper.UserRoleRelationMapper;
import com.simple.blog.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户缓存管理 Service 实现类
 */
@Service
public class UserCacheServiceImpl implements UserCacheService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    /**
     * Redis 用户缓存 key 前缀
     */
    private static final String REDIS_USER_PREFIX = "blog_user_";

    /**
     * Redis 资源缓存 key 前缀
     */
    private static final String REDIS_RESOURCE_PREFIX = "blog_resource_";

    /**
     * Redis 缓存时间(s) (24*60*60)
     */
    private static final Long REDIS_EXPIRATION = 86400L;

    @Override
    public User getUserCache(String username) {
        //  从Redis缓存中查找用户
        String key = REDIS_USER_PREFIX + username;
        User user = (User) redisService.get(key);
        if (null != user) {
            //  用户缓存存在，直接返回
            return user;
        }

        //  用户缓存不存在，从数据库中查找用户
        user = userMapper.findByUsername(username);
        if (null != user) {
            //  数据库中存在用户，放入Redis缓存
            setUserCache(user);
        }

        return user;
    }

    @Override
    public void setUserCache(User user) {
        String key = REDIS_USER_PREFIX + user.getUsername();
        redisService.set(key, user, REDIS_EXPIRATION);
    }

    @Override
    public void delUserCache(Long userId) {
        User user = userMapper.findById(userId);
        if (null != user) {
            String key = REDIS_USER_PREFIX + user.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public List<Resource> getResourceCache(Long userId) {
        //  从Redis缓存中查找用户的资源列表
        String key = REDIS_RESOURCE_PREFIX + userId;
        List<Resource> resourceList =  (List<Resource>) redisService.get(key);
        if (CollUtil.isNotEmpty(resourceList)) {
            //  用户的资源缓存存在，直接返回
            return resourceList;
        }

        //  用户的资源缓存不存在，从数据库中查找
        resourceList = userRoleRelationMapper.findResourceList(userId);
        if (CollUtil.isNotEmpty(resourceList)) {
            //  数据库中存在用户的资源，放入Redis缓存
            setResourceCache(userId, resourceList);
        }

        return resourceList;
    }

    @Override
    public void setResourceCache(Long userId, List<Resource> resourceList) {
        String key = REDIS_RESOURCE_PREFIX + userId;
        redisService.set(key, resourceList, REDIS_EXPIRATION);
    }

    @Override
    public void delResourceCacheByUserId(Long userId) {
        String key = REDIS_RESOURCE_PREFIX + userId;
        redisService.del(key);
    }

    @Override
    public void delResourceCacheByRoleId(Long roleId) {
        List<Long> userIds = userRoleRelationMapper.findUserIdsByRoleId(roleId);
        if (CollUtil.isNotEmpty(userIds)) {
            List<String> keys = userIds.stream()
                    .map(userId -> REDIS_RESOURCE_PREFIX + userId)
                    .collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceCacheByResourceId(Long resourceId) {
        List<Long> userIds = userRoleRelationMapper.findUserIdsByResourceId(resourceId);
        if (CollUtil.isNotEmpty(userIds)) {
            List<String> keys = userIds.stream()
                    .map(userId -> REDIS_RESOURCE_PREFIX + userId)
                    .collect(Collectors.toList());
            redisService.del(keys);
        }
    }

}
