package com.simple.blog.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.common.base.BaseMapper;
import com.simple.blog.entity.Role;
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
