package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.data.entity.Category;
import com.simple.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 分类管理 Controller
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     */
    @PostMapping(value = "/create")
    public CommonResult<Void> create(@Valid @RequestBody Category category) {
        int count = categoryService.create(category);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除分类
     */
    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        categoryService.delete(id);
        return CommonResult.success();
    }

    /**
     * 修改分类
     */
    @PostMapping(value = "/update")
    public CommonResult<Void> update(@Valid @RequestBody Category category) {
        int count = categoryService.update(category);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查找分类
     */
    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Category> detail(@PathVariable long id) {
        Category result = categoryService.detail(id);
        return CommonResult.success(result);
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/list")
    public CommonResult<CommonPage<Category>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name
    ) {
        Page<Category> page = categoryService.list(pageNum, pageSize, name);
        return CommonResult.success(new CommonPage<>(page));
    }
    
}
