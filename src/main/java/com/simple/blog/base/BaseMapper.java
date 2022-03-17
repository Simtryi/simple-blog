package com.simple.blog.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T> {

    /**
     * 插入对象
     */
    int insert(T t);

    /**
     * 批量插入对象
     */
    int insertBatch(@Param("list") List<T> list);

    /**
     * 根据Id删除数据
     */
    int deleteById(Long id);

    /**
     * 根据Id列表删除数据
     */
    int deleteByIds(@Param("list") List<Long> list);

    /**
     * 更新数据
     */
    int update(T t);

    /**
     * 根据Id查询数据
     */
    T selectById(Long id);

    /**
     * 根据Id列表查询数据
     */
    List<T> selectByIds(@Param("list") List<Long> list);

}
