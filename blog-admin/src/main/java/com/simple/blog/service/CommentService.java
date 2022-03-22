package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.entity.Comment;

/**
 * 评论管理 Service
 */
public interface CommentService {

    /**
     * 添加评论
     */
    int create(Comment comment);

    /**
     * 删除评论
     */
    int delete(Long id);

    /**
     * 修改评论
     */
    int update(Comment comment);

    /**
     * 查询评论
     */
    Comment detail(Long id);

    /**
     * 分页查询评论
     */
    Page<Comment> list(int pageNum, int pageSize);

}
