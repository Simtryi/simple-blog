package com.simple.blog.search.repository;

import com.simple.blog.search.entity.ESBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * ES 博客 Repository
 */
public interface ESBlogRepository extends ElasticsearchRepository<ESBlog, Long> {

    /**
     * 根据标题搜索 ES 博客
     * @param title     标题
     * @param pageable  分页信息
     */
    Page<ESBlog> findByTitle(String title, Pageable pageable);

}
