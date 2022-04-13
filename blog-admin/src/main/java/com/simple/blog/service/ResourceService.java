package com.simple.blog.service;

import com.github.pagehelper.Page;
import com.simple.blog.entity.Resource;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资源管理 Service
 */
public interface ResourceService {

    /**
     * 添加资源
     */
    int create(Resource resource);

    /**
     * 删除资源
     */
    @Transactional
    void delete(Long id);

    /**
     * 修改资源
     */
    int update(Resource resource);

    /**
     * 查询资源
     */
    Resource detail(Long id);

    /**
     * 分页查询资源
     */
    Page<Resource> list(int pageNum, int pageSize, String name, String url);

}
