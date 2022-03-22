package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.entity.Tag;

/**
 * 标签管理 Service
 */
public interface TagService {

    /**
     * 添加标签
     */
    int create(Tag tag);

    /**
     * 删除标签
     */
    int delete(Long id);

    /**
     * 修改标签
     */
    int update(Tag tag);

    /**
     * 查询标签
     */
    Tag detail(Long id);

    /**
     * 分页查询标签
     */
    Page<Tag> list(int pageNum, int pageSize, String name);

}
