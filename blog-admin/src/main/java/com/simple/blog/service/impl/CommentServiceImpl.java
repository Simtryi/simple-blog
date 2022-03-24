package com.simple.blog.service.impl;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.entity.Comment;
import com.simple.blog.mapper.CommentMapper;
import com.simple.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 评论管理 Service 实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public int create(Comment comment) {
        comment.setCreatedAt(new Date());
        return commentMapper.insert(comment);
    }

    @Override
    public int delete(Long id) {
        return commentMapper.deleteById(id);
    }

    @Override
    public int update(Comment comment) {
        if (null == comment.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填");
        }

        Comment commentDB = commentMapper.selectById(comment.getId());
        if (null == commentDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + comment.getId() + "对应的评论不存在");
        }

        comment.setUpdatedAt(new Date());
        return commentMapper.update(comment);
    }

    @Override
    public Comment detail(Long id) {
        return commentMapper.selectById(id);
    }

    @Override
    public Page<Comment> list(int pageNum, int pageSize) {
        return commentMapper.page(pageNum, pageSize);
    }
    
}