package com.simple.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.data.entity.Resource;
import com.simple.blog.data.entity.Role;
import com.simple.blog.data.entity.User;
import com.simple.blog.data.entity.UserRoleRelation;
import com.simple.blog.data.mapper.UserMapper;
import com.simple.blog.data.mapper.UserRoleRelationMapper;
import com.simple.blog.service.AdminService;
import com.simple.blog.service.UserCacheService;
import com.simple.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户管理 Service 实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        return adminService.register(user);
    }

    @Override
    public void delete(Long id) {
        //  由于用户删除，删除用户缓存
        userCacheService.delUserCache(id);
        //  由于用户删除，删除用户的资源缓存
        userCacheService.delResourceCacheByUserId(id);

        //  删除用户角色关系
        userRoleRelationMapper.deleteByUserId(id);
        //  删除用户
        userMapper.deleteById(id);
    }

    @Override
    public int update(User user) {
        if (null == user.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填！");
        }

        User userDB = userMapper.findById(user.getId());
        if (null == userDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + user.getId() + "对应的用户不存在");
        }

        if (!user.getUsername().equals(userDB.getUsername())) {
            User userDB2 = userMapper.findByUsername(user.getUsername());
            if (null != userDB2) {
                Asserts.fail(ResultCode.BAD_REQUEST, "用户名已存在");
            }
        }

        //  对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setUpdatedAt(new Date());

        //  由于用户更新，删除用户缓存
        userCacheService.delUserCache(user.getId());

        return userMapper.update(user);
    }

    @Override
    public User detail(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public Page<User> list(int pageNum, int pageSize, String username, String nickname, String email) {
        return userMapper.page(pageNum, pageSize, username, nickname, email);
    }

    @Override
    public List<Role> getRoleList(Long userId) {
        return userRoleRelationMapper.findRoleList(userId);
    }

    @Override
    public List<Resource> getResourceList(Long userId) {
        return userRoleRelationMapper.findResourceList(userId);
    }

    @Override
    public void assignRole(Long userId, List<Long> roleIds) {
        //  删除原有关系
        userRoleRelationMapper.deleteByUserId(userId);

        //  建立新关系
        if (!CollUtil.isEmpty(roleIds)) {
            List<UserRoleRelation> list = new ArrayList<>();
            roleIds.forEach(roleId -> {
                UserRoleRelation userRoleRelation = new UserRoleRelation();
                userRoleRelation.setUserId(userId);
                userRoleRelation.setRoleId(roleId);
                list.add(userRoleRelation);
            });
            userRoleRelationMapper.saveAll(list);
        }

        //  由于用户角色更新，删除用户的资源缓存
        userCacheService.delResourceCacheByUserId(userId);
    }

}
