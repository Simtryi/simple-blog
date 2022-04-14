package com.simple.blog.portal.controller;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.entity.Category;
import com.simple.blog.portal.service.CategoryShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类展示 Controller
 */
@RestController
@RequestMapping("/portal/category")
public class CategoryShowController {

    @Autowired
    private CategoryShowService categoryShowService;

    /**
     * 展示所有分类
     */
    @RequestMapping(value = "/all")
    public CommonResult<List<Category>> listAll() {
        List<Category> categoryList = categoryShowService.listAll();
        return CommonResult.success(categoryList);
    }

    /**
     * 展示随机分类
     */
    @RequestMapping(value = "/random")
    public CommonResult<List<Category>> listRandom() {
        List<Category> categoryList = categoryShowService.listRandom();
        return CommonResult.success(categoryList);
    }

    /**
     * 获取分类的博客列表
     */
    @RequestMapping(value = "/blog/list/{id}")
    public CommonResult<List<Blog>> getBlogList(@PathVariable long id) {
        List<Blog> blogList = categoryShowService.getBlogList(id);
        return CommonResult.success(blogList);
    }

}
