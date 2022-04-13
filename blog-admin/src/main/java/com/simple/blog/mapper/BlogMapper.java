package com.simple.blog.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 博客管理 Mapper
 */
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 分页
     */
    Page<Blog> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("title") String title
    );

    /**
     * 查询所有博客
     */
    List<Blog> findAll();

}
