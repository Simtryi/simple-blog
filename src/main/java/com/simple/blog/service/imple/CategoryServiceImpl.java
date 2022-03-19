package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
import com.simple.blog.common.ResultCode;
import com.simple.blog.entity.Category;
import com.simple.blog.exception.Asserts;
import com.simple.blog.mapper.CategoryMapper;
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
    CategoryMapper categoryMapper;

    @Override
    public Category create(Category category) {
        category.setCreatedAt(new Date());
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        Category category = categoryMapper.selectById(id);
        if (null != category) {
            categoryMapper.deleteById(id);
        }
    }

    @Override
    public Category update(Category category) {
        if (null == category.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填！");
        }

        Category categoryDB = categoryMapper.selectById(category.getId());
        if (null == categoryDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + category.getId() + "对应的分类不存在");
        }

        category.setUpdatedAt(new Date());
        categoryMapper.update(category);
        return category;
    }

    @Override
    public Category detail(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public Page<Category> list(int pageNum, int pageSize, String name) {
        return categoryMapper.page(pageNum, pageSize, name);
    }
    
}
