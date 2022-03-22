package com.simple.blog.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 博客管理 Mapper
 */
@Mapper
@Repository
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 分页
     */
    Page<Blog> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("title") String title
    );

}
