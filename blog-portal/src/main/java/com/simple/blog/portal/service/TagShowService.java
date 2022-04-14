package com.simple.blog.portal.service;

import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.entity.Tag;

import java.util.List;

/**
 * 标签展示 Service
 */
public interface TagShowService {

    /**
     * 展示所有标签
     */
    List<Tag> listAll();

    /**
     * 展示随机标签
     */
    List<Tag> listRandom();

    /**
     * 获取标签的博客列表
     */
    List<Blog> getBlogList(Long tagId);

}
