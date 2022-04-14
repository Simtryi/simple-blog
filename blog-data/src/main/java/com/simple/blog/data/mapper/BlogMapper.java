package com.simple.blog.data.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.data.base.BaseMapper;
import com.simple.blog.data.entity.Blog;
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

    /**
     * 根据分类Id查找分类的博客列表
     */
    List<Blog> findByCategoryId(Long categoryId);

}
