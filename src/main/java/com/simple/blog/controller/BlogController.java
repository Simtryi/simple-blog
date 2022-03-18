package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.Pager;
import com.simple.blog.common.WebResult;
import com.simple.blog.entity.Blog;
import com.simple.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @PostMapping(value = "/create")
    public WebResult<Blog> create(@RequestParam Blog blog) {
        Blog result = blogService.create(blog);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/delete/{id}")
    public WebResult<Void> delete(@PathVariable long id) {
        blogService.delete(id);
        return WebResult.success();
    }

    @PostMapping(value = "/update")
    public WebResult<Blog> update(@RequestParam Blog blog) {
        Blog result = blogService.update(blog);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/detail/{id}")
    public WebResult<Blog> detail(@PathVariable long id) {
        Blog result = blogService.detail(id);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/list")
    public WebResult<Pager<Blog>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String title
    ) {
        Page<Blog> page = blogService.list(pageNum, pageSize, title);
        return WebResult.success(new Pager<>(page));
    }
    
}
