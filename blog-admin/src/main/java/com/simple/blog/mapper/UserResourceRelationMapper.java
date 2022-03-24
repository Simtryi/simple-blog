package com.simple.blog.mapper;

import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.UserResourceRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户资源关系 Mapper
 */
@Mapper
@Repository
public interface UserResourceRelationMapper extends BaseMapper<UserResourceRelation> {
}
