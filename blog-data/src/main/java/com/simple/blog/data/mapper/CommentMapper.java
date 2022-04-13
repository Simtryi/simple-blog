package com.simple.blog.data.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.data.base.BaseMapper;
import com.simple.blog.data.entity.Comment;
import org.apache.ibatis.annotations.Param;

/**
 * 评论管理 Mapper
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 分页
     */
    Page<Comment> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize
    );

}
