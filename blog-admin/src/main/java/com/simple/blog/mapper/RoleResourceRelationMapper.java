package com.simple.blog.mapper;

import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Resource;
import com.simple.blog.entity.RoleResourceRelation;

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
