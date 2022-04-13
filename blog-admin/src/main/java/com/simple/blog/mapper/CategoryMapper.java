package com.simple.blog.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Category;
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
