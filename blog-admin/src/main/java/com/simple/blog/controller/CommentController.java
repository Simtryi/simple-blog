package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.entity.Comment;
import com.simple.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论管理 Controller
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping(value = "/create")
    public CommonResult<Void> create(@RequestBody Comment comment) {
        int count = commentService.create(comment);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        commentService.delete(id);
        return CommonResult.success();
    }

    @PostMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody Comment comment) {
        int count = commentService.update(comment);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Comment> detail(@PathVariable long id) {
        Comment result = commentService.detail(id);
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/list")
    public CommonResult<CommonPage<Comment>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Comment> page = commentService.list(pageNum, pageSize);
        return CommonResult.success(new CommonPage<>(page));
    }
    
}