package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.entity.Tag;
import com.simple.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 标签管理 Controller
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @PostMapping(value = "/create")
    public CommonResult<Void> create(@RequestBody Tag tag) {
        int count = tagService.create(tag);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        tagService.delete(id);
        return CommonResult.success();
    }

    @PostMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody Tag tag) {
        int count = tagService.update(tag);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Tag> detail(@PathVariable long id) {
        Tag result = tagService.detail(id);
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/list")
    public CommonResult<CommonPage<Tag>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name
    ) {
        Page<Tag> page = tagService.list(pageNum, pageSize, name);
        return CommonResult.success(new CommonPage<>(page));
    }
    
}
