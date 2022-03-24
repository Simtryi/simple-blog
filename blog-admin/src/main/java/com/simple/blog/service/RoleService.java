package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.entity.Role;

/**
 * 角色管理 Service
 */
public interface RoleService {

    /**
     * 添加角色
     */
    int create(Role role);

    /**
     * 删除角色
     */
    int delete(Long id);

    /**
     * 修改角色
     */
    int update(Role role);

    /**
     * 查询角色
     */
    Role detail(Long id);

    /**
     * 分页查询角色
     */
    Page<Role> list(int pageNum, int pageSize, String name);

}
