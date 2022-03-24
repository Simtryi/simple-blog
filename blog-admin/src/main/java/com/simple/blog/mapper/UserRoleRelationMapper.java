package com.simple.blog.mapper;

import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.UserRoleRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关系 Mapper
 */
@Mapper
@Repository
public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation> {
}
