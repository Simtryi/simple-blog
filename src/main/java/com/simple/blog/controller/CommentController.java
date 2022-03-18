package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.Pager;
import com.simple.blog.common.WebResult;
import com.simple.blog.entity.Comment;
import com.simple.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping(value = "/create")
    public WebResult<Comment> create(@RequestParam Comment comment) {
        Comment result = commentService.create(comment);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/delete/{id}")
    public WebResult<Void> delete(@PathVariable long id) {
        commentService.delete(id);
        return WebResult.success();
    }

    @PostMapping(value = "/update")
    public WebResult<Comment> update(@RequestParam Comment comment) {
        Comment result = commentService.update(comment);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/detail/{id}")
    public WebResult<Comment> detail(@PathVariable long id) {
        Comment result = commentService.detail(id);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/list")
    public WebResult<Pager<Comment>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Comment> page = commentService.list(pageNum, pageSize);
        return WebResult.success(new Pager<>(page));
    }
    
}
