package com.simple.blog.portal.service.impl;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.common.util.StringUtil;
import com.simple.blog.data.entity.Comment;
import com.simple.blog.data.mapper.CommentMapper;
import com.simple.blog.portal.service.CommentService;
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
        return commentMapper.save(comment);
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

        Comment commentDB = commentMapper.findById(comment.getId());
        if (null == commentDB) {
            Asserts.fail(ResultCode.NOT_FOUND, StringUtil.format("id={}对应的评论不存在", comment.getId()));
        }

        comment.setUpdatedAt(new Date());
        return commentMapper.update(comment);
    }

    @Override
    public Comment detail(Long id) {
        return commentMapper.findById(id);
    }

    @Override
    public Page<Comment> list(int pageNum, int pageSize) {
        return commentMapper.page(pageNum, pageSize);
    }
    
}
