package com.simple.blog.portal.service.impl;

import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.entity.Category;
import com.simple.blog.data.mapper.BlogMapper;
import com.simple.blog.data.mapper.CategoryMapper;
import com.simple.blog.portal.service.CategoryShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 分类展示 Service 实现类
 */
@Service
public class CategoryShowServiceImpl implements CategoryShowService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Category> listAll() {
        return categoryMapper.findAll();
    }

    @Override
    public List<Category> listRandom() {
        List<Category> categoryList = categoryMapper.findAll();
        Collections.shuffle(categoryList);
        int size = Math.min(10, categoryList.size());
        return categoryList.subList(0, size);
    }

    @Override
    public List<Blog> getBlogList(Long categoryId) {
        return blogMapper.findByCategoryId(categoryId);
    }

}
