package com.simple.blog.data.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.data.base.BaseMapper;
import com.simple.blog.data.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
 * 角色管理 Mapper
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 分页
     */
    Page<Role> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("name") String name
    );

}
