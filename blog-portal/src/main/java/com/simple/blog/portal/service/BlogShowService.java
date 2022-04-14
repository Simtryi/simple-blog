package com.simple.blog.portal.service;

import com.simple.blog.data.entity.Blog;

/**
 * 博客展示 Service
 */
public interface BlogShowService {

    /**
     * 查询博客
     */
    Blog detail(Long id);

}
