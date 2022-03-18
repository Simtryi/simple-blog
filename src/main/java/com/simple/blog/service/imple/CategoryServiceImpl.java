package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
import com.simple.blog.entity.Category;
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
        //  todo 校验
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
            //  todo 抛出异常
        }

        Category categoryDB = categoryMapper.selectById(category.getId());
        if (null == categoryDB) {
            //  todo 抛出异常
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
