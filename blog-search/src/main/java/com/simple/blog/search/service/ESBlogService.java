package com.simple.blog.search.service;

import com.simple.blog.search.entity.ESBlog;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * ES 博客 Service
 */
public interface ESBlogService {

    /**
     * 从数据库中导入所有博客到 ES
     */
    int create();

    /**
     * 根据 Id 删除博客
     */
    void delete(Long id);

    /**
     * 批量删除博客
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索博客
     */
    Page<ESBlog> search(String keyword, Integer pageNum, Integer pageSize);

    Optional<ESBlog> findById(Long id);

}
