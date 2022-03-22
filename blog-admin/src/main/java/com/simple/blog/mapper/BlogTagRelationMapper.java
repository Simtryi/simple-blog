package com.simple.blog.mapper;

import com.simple.blog.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 博客标签关系 Mapper
 */
@Mapper
@Repository
public interface BlogTagRelationMapper extends BaseMapper<BlogTagRelationMapper> {

}
