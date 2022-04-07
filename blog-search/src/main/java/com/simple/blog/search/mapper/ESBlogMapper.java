package com.simple.blog.search.mapper;

import com.simple.blog.search.entity.ESBlog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ES 博客 Mapper
 */
@Mapper
@Repository
public interface ESBlogMapper {

    /**
     * 查询全部博客
     */
    List<ESBlog> selectAll();

}
