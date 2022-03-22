package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.entity.Blog;
import com.simple.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 博客管理 Controller
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @PostMapping(value = "/create")
    public CommonResult<Void> create(@RequestBody Blog blog) {
        int count = blogService.create(blog);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        blogService.delete(id);
        return CommonResult.success();
    }

    @PostMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody Blog blog) {
        int count = blogService.update(blog);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Blog> detail(@PathVariable long id) {
        Blog result = blogService.detail(id);
        return CommonResult.success(result);
    }

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
