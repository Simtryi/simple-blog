package com.simple.blog.mapper;

import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Resource;
import com.simple.blog.entity.Role;
import com.simple.blog.entity.UserRoleRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色关系 Mapper
 */
@Mapper
@Repository
public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation> {

    /**
     * 根据用户Id查找用户的角色列表
     */
    List<Role> selectRoleList(Long userId);

    /**
     * 根据用户Id查找用户的资源列表
     */
    List<Resource> selectResourceList(Long userId);

    /**
     * 根据用户Id删除所有用户角色关系
     */
    int deleteByUserId(Long userId);

    /**
     * 根据角色Id查找与角色关联的用户Id列表
     */
    List<Long> selectUserIdsByRoleId(Long roleId);

    /**
     * 根据资源Id查找与资源关联的用户Id列表
     */
    List<Long> selectUserIdsByResourceId(Long resourceId);

}
