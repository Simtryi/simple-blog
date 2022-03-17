package com.simple.blog.mapper;

import com.simple.blog.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogTagRelationMapper extends BaseMapper<BlogTagRelationMapper> {

}
