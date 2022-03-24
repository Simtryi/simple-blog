package com.simple.blog.service.impl;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.entity.Role;
import com.simple.blog.mapper.RoleMapper;
import com.simple.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 角色管理 Service 实现类
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int create(Role role) {
        role.setCreatedAt(new Date());
        return roleMapper.insert(role);
    }

    @Override
    public int delete(Long id) {
        return roleMapper.deleteById(id);
    }

    @Override
    public int update(Role role) {
        if (null == role.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填");
        }

        Role roleDB = roleMapper.selectById(role.getId());
        if (null == roleDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + role.getId() + "对应的角色不存在");
        }

        role.setUpdatedAt(new Date());
        return roleMapper.update(role);
    }

    @Override
    public Role detail(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Page<Role> list(int pageNum, int pageSize, String name) {
        return roleMapper.page(pageNum, pageSize, name);
    }

}
