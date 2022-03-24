package com.simple.blog.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源管理 Mapper
 */
@Mapper
@Repository
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
    List<Resource> selectAll();

}
