package com.simple.blog.data.mapper;

import com.simple.blog.data.base.BaseMapper;

/**
 * 博客标签关系 Mapper
 */
public interface BlogTagRelationMapper extends BaseMapper<BlogTagRelationMapper> {

    /**
     * 根据博客Id删除所有博客标签关系
     */
    int deleteByBlogId(Long blogId);

    /**
     * 根据标签Id删除所有博客标签关系
     */
    int deleteByTagId(Long tagId);

}
