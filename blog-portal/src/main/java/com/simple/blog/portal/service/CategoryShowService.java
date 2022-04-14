package com.simple.blog.portal.service;

import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.entity.Category;

import java.util.List;

/**
 * 分类展示 Service
 */
public interface CategoryShowService {

    /**
     * 展示所有分类
     */
    List<Category> listAll();

    /**
     * 展示随机分类
     */
    List<Category> listRandom();

    /**
     * 获取分类的博客列表
     */
    List<Blog> getBlogList(Long categoryId);

}
