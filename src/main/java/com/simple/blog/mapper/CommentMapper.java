package com.simple.blog.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.base.BaseMapper;
import com.simple.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 分页
     */
    Page<Comment> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize
    );

}
