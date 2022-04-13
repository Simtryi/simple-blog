package com.simple.blog.portal.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.data.entity.Comment;
import com.simple.blog.portal.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 评论管理 Controller
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     */
    @PostMapping(value = "/create")
    public CommonResult<Void> create(@Valid @RequestBody Comment comment) {
        int count = commentService.create(comment);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除评论
     */
    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        commentService.delete(id);
        return CommonResult.success();
    }

    /**
     * 修改评论
     */
    @PostMapping(value = "/update")
    public CommonResult<Void> update(@Valid @RequestBody Comment comment) {
        int count = commentService.update(comment);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查找评论
     */
    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Comment> detail(@PathVariable long id) {
        Comment result = commentService.detail(id);
        return CommonResult.success(result);
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/list")
    public CommonResult<CommonPage<Comment>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Comment> page = commentService.list(pageNum, pageSize);
        return CommonResult.success(new CommonPage<>(page));
    }
    
}
