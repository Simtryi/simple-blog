package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.entity.Resource;
import com.simple.blog.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    void delete(Long id);

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

    /**
     * 获取角色的资源列表
     */
    List<Resource> getResourceList(Long roleId);

    /**
     * 为角色分配资源
     */
    @Transactional
    void assignResource(Long roleId, List<Long> resourceIds);

}
