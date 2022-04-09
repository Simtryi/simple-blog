package com.simple.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.common.util.StringUtil;
import com.simple.blog.entity.Resource;
import com.simple.blog.entity.Role;
import com.simple.blog.entity.RoleResourceRelation;
import com.simple.blog.mapper.RoleMapper;
import com.simple.blog.mapper.RoleResourceRelationMapper;
import com.simple.blog.service.RoleService;
import com.simple.blog.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色管理 Service 实现类
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceRelationMapper roleResourceRelationMapper;

    @Autowired
    private UserCacheService userCacheService;

    @Override
    public int create(Role role) {
        role.setCreatedAt(new Date());
        role.setCount(0);
        return roleMapper.insert(role);
    }

    @Override
    public void delete(Long id) {
        //  删除用户资源缓存
        userCacheService.delResourceCacheByRoleId(id);

        //  删除角色资源关系
        roleResourceRelationMapper.deleteByRoleId(id);
        //  删除角色
        roleMapper.deleteById(id);
    }

    @Override
    public int update(Role role) {
        if (null == role.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填");
        }

        Role roleDB = roleMapper.selectById(role.getId());
        if (null == roleDB) {
            Asserts.fail(ResultCode.NOT_FOUND, StringUtil.format("id={}对应的角色不存在", role.getId()));
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

    @Override
    public List<Resource> getResourceList(Long roleId) {
        return roleResourceRelationMapper.findResourceList(roleId);
    }

    @Override
    public void assignResource(Long roleId, List<Long> resourceIds) {
        //  删除原有关系
        roleResourceRelationMapper.deleteByRoleId(roleId);

        //  建立新关系
        if (!CollUtil.isEmpty(resourceIds)) {
            List<RoleResourceRelation> list = new ArrayList<>();
            resourceIds.forEach(resourceId -> {
                RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
                roleResourceRelation.setRoleId(roleId);
                roleResourceRelation.setResourceId(resourceId);
                list.add(roleResourceRelation);
            });
            roleResourceRelationMapper.insertBatch(list);
        }

        //  由于角色资源更新，删除用户资源缓存
        userCacheService.delResourceCacheByRoleId(roleId);
    }

}
