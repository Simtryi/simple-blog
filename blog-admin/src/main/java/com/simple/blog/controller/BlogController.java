package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.data.entity.Blog;
import com.simple.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 博客管理 Controller
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 添加博客
     */
    @PostMapping(value = "/create")
    public CommonResult<Void> create(@Valid @RequestBody Blog blog) {
        int count = blogService.create(blog);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除博客
     */
    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        blogService.delete(id);
        return CommonResult.success();
    }

    /**
     * 修改博客
     */
    @PostMapping(value = "/update")
    public CommonResult<Void> update(@Valid @RequestBody Blog blog) {
        int count = blogService.update(blog);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查找博客
     */
    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Blog> detail(@PathVariable long id) {
        Blog result = blogService.detail(id);
        return CommonResult.success(result);
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/list")
    public CommonResult<CommonPage<Blog>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String title
    ) {
        Page<Blog> page = blogService.list(pageNum, pageSize, title);
        return CommonResult.success(new CommonPage<>(page));
    }
    
}
