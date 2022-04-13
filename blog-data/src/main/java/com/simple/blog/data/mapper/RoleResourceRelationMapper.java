package com.simple.blog.data.mapper;

import com.simple.blog.data.base.BaseMapper;
import com.simple.blog.data.entity.Resource;
import com.simple.blog.data.entity.RoleResourceRelation;

import java.util.List;

/**
 * 角色资源关系 Mapper
 */
public interface RoleResourceRelationMapper extends BaseMapper<RoleResourceRelation> {

    /**
     * 根据角色Id查找角色的资源列表
     */
    List<Resource> findResourceList(Long roleId);

    /**
     * 根据角色Id删除所有角色资源关系
     */
    int deleteByRoleId(Long roleId);

    /**
     * 根据资源Id删除所有角色资源关系
     */
    int deleteByResourceId(Long resourceId);

}
