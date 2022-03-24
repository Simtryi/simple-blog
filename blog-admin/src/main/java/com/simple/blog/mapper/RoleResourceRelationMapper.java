package com.simple.blog.mapper;

import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.RoleResourceRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色资源关系 Mapper
 */
@Mapper
@Repository
public interface RoleResourceRelationMapper extends BaseMapper<RoleResourceRelation> {
}
