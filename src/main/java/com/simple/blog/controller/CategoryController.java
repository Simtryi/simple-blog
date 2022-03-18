package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.Pager;
import com.simple.blog.common.WebResult;
import com.simple.blog.entity.Category;
import com.simple.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping(value = "/create")
    public WebResult<Category> create(@RequestParam Category category) {
        Category result = categoryService.create(category);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/delete/{id}")
    public WebResult<Void> delete(@PathVariable long id) {
        categoryService.delete(id);
        return WebResult.success();
    }

    @PostMapping(value = "/update")
    public WebResult<Category> update(@RequestParam Category category) {
        Category result = categoryService.update(category);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/detail/{id}")
    public WebResult<Category> detail(@PathVariable long id) {
        Category result = categoryService.detail(id);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/list")
    public WebResult<Pager<Category>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name
    ) {
        Page<Category> page = categoryService.list(pageNum, pageSize, name);
        return WebResult.success(new Pager<>(page));
    }
    
}
