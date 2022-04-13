package com.simple.blog.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Tag;
import org.apache.ibatis.annotations.Param;

/**
 * 标签管理 Mapper
 */
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 分页
     */
    Page<Tag> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("name") String name
    );

}
