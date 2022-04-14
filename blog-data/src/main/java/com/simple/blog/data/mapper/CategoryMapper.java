package com.simple.blog.data.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.data.base.BaseMapper;
import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类管理 Mapper
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 分页
     */
    Page<Category> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("name") String name
    );

    /**
     * 查询所有分类
     */
    List<Category> findAll();

}
