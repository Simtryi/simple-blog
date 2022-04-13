package com.simple.blog.data.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.data.base.BaseMapper;
import com.simple.blog.data.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户管理 Mapper
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页
     */
    Page<User> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("username") String username,
            @Param("nickname") String nickname,
            @Param("email") String email
    );

    /**
     * 根据用户名查找用户
     */
    User findByUsername(String username);

    /**
     * 根据用户名和密码查找用户
     */
    User findByUsernameAndPassword(
            @Param("username") String username,
            @Param("password") String password
    );

}
