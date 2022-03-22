package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.entity.Category;

/**
 * 分类管理 Service
 */
public interface CategoryService {

    /**
     * 添加分类
     */
    int create(Category category);

    /**
     * 删除分类
     */
    int delete(Long id);

    /**
     * 修改分类
     */
    int update(Category category);

    /**
     * 查询分类
     */
    Category detail(Long id);

    /**
     * 分页查询分类
     */
    Page<Category> list(int pageNum, int pageSize, String name);

}
