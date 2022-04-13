package com.simple.blog.service.impl;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.common.util.StringUtil;
import com.simple.blog.data.entity.Category;
import com.simple.blog.data.mapper.CategoryMapper;
import com.simple.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 分类管理 Service 实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int create(Category category) {
        category.setCreatedAt(new Date());
        return categoryMapper.save(category);
    }

    @Override
    public int delete(Long id) {
        return categoryMapper.deleteById(id);
    }

    @Override
    public int update(Category category) {
        if (null == category.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填");
        }

        Category categoryDB = categoryMapper.findById(category.getId());
        if (null == categoryDB) {
            Asserts.fail(ResultCode.NOT_FOUND, StringUtil.format("id={}对应的分类不存在", category.getId()));
        }

        category.setUpdatedAt(new Date());
        return categoryMapper.update(category);
    }

    @Override
    public Category detail(Long id) {
        return categoryMapper.findById(id);
    }

    @Override
    public Page<Category> list(int pageNum, int pageSize, String name) {
        return categoryMapper.page(pageNum, pageSize, name);
    }
    
}
