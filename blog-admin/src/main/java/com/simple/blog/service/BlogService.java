package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.data.entity.Blog;
import org.springframework.transaction.annotation.Transactional;

/**
 * 博客管理 Service
 */
public interface BlogService {

    /**
     * 添加博客
     */
    int create(Blog blog);

    /**
     * 删除博客
     */
    @Transactional
    int delete(Long id);

    /**
     * 修改博客
     */
    int update(Blog blog);

    /**
     * 查询博客
     */
    Blog detail(Long id);

    /**
     * 分页查询博客
     */
    Page<Blog> list(int pageNum, int pageSize, String title);
    
}
