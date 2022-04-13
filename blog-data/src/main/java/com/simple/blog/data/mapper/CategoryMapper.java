package com.simple.blog.data.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.data.base.BaseMapper;
import com.simple.blog.data.entity.Category;
import org.apache.ibatis.annotations.Param;

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

}
