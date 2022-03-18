package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.Pager;
import com.simple.blog.common.WebResult;
import com.simple.blog.entity.Tag;
import com.simple.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping(value = "/create")
    public WebResult<Tag> create(@RequestParam Tag tag) {
        Tag result = tagService.create(tag);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/delete/{id}")
    public WebResult<Void> delete(@PathVariable long id) {
        tagService.delete(id);
        return WebResult.success();
    }

    @PostMapping(value = "/update")
    public WebResult<Tag> update(@RequestParam Tag tag) {
        Tag result = tagService.update(tag);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/detail/{id}")
    public WebResult<Tag> detail(@PathVariable long id) {
        Tag result = tagService.detail(id);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/list")
    public WebResult<Pager<Tag>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name
    ) {
        Page<Tag> page = tagService.list(pageNum, pageSize, name);
        return WebResult.success(new Pager<>(page));
    }
    
}
