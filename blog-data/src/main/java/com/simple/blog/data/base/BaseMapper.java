package com.simple.blog.data.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T> {

    /**
     * 插入对象
     */
    int save(T t);

    /**
     * 批量插入对象
     */
    int saveAll(@Param("list") List<T> list);

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
    T findById(Long id);

    /**
     * 根据Id列表查询数据
     */
    List<T> findByIds(@Param("list") List<Long> list);

}
