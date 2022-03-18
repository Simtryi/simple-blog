package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.entity.Tag;

public interface TagService {

    /**
     * 添加标签
     */
    Tag create(Tag tag);

    /**
     * 删除标签
     */
    void delete(Long id);

    /**
     * 修改标签
     */
    Tag update(Tag tag);

    /**
     * 查询标签
     */
    Tag detail(Long id);

    /**
     * 分页查询标签
     */
    Page<Tag> list(int pageNum, int pageSize, String name);

}
