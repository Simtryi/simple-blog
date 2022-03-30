package com.simple.blog.mapper;

import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Resource;
import com.simple.blog.entity.RoleResourceRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色资源关系 Mapper
 */
@Mapper
@Repository
public interface RoleResourceRelationMapper extends BaseMapper<RoleResourceRelation> {

    /**
     * 根据角色Id查找角色的资源列表
     */
    List<Resource> selectResourceList(Long roleId);

    /**
     * 根据角色Id删除所有角色资源关系
     */
    int deleteByRoleId(Long roleId);


}
