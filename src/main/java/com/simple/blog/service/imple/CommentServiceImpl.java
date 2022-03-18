package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
import com.simple.blog.entity.Comment;
import com.simple.blog.mapper.CommentMapper;
import com.simple.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public Comment create(Comment comment) {
        //  todo 校验
        commentMapper.insert(comment);
        return comment;
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (null != comment) {
            commentMapper.deleteById(id);
        }
    }

    @Override
    public Comment update(Comment comment) {
        if (null == comment.getId()) {
            //  todo 抛出异常
        }

        Comment commentDB = commentMapper.selectById(comment.getId());
        if (null == commentDB) {
            //  todo 抛出异常
        }

        commentMapper.update(comment);
        return comment;
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
