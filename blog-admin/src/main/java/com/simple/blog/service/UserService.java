package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.data.entity.Resource;
import com.simple.blog.data.entity.Role;
import com.simple.blog.data.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理 Service
 */
public interface UserService {

    /**
     * 添加用户
     */
    User create(User user);

    /**
     * 删除用户
     */
    @Transactional
    void delete(Long id);

    /**
     * 修改用户
     */
    int update(User user);

    /**
     * 查询用户
     */
    User detail(Long id);

    /**
     * 分页查询用户
     */
    Page<User> list(int pageNum, int pageSize, String username, String nickname, String email);

    /**
     * 获取用户的角色列表
     */
    List<Role> getRoleList(Long userId);

    /**
     * 获取用户的资源列表
     */
    List<Resource> getResourceList(Long userId);

    /**
     * 为用户分配角色
     */
    @Transactional
    void assignRole(Long userId, List<Long> roleIds);

}
