package com.simple.blog.mapper;

import com.github.pagehelper.Page;
import com.simple.blog.base.BaseMapper;
import com.simple.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
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

}
