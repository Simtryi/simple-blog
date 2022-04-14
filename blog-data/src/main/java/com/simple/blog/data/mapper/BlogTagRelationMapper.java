package com.simple.blog.data.mapper;

import com.simple.blog.data.base.BaseMapper;
import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.entity.BlogTagRelation;

import java.util.List;

/**
 * 博客标签关系 Mapper
 */
public interface BlogTagRelationMapper extends BaseMapper<BlogTagRelation> {

    /**
     * 根据博客Id删除所有博客标签关系
     */
    int deleteByBlogId(Long blogId);

    /**
     * 根据标签Id删除所有博客标签关系
     */
    int deleteByTagId(Long tagId);

    /**
     * 根据标签Id查找标签的博客列表
     */
    List<Blog> findBlogList(Long tagId);

}
