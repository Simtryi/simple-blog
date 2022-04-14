package com.simple.blog.portal.controller;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.data.entity.Blog;
import com.simple.blog.portal.service.BlogShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客展示 Controller
 */
@RestController
@RequestMapping("/portal/blog")
public class BlogShowController {

    @Autowired
    private BlogShowService blogShowService;

    /**
     * 展示博客
     */
    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Blog> detail(@PathVariable long id) {
        Blog blog = blogShowService.detail(id);
        return CommonResult.success(blog);
    }

}
