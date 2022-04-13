package com.simple.blog.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Comment;
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
