package com.simple.blog.data.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.data.base.BaseMapper;
import com.simple.blog.data.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源管理 Mapper
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 分页
     */
    Page<Resource> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("name") String name,
            @Param("url") String url
    );

    /**
     * 查询全部资源
     */
    List<Resource> findAll();

}
