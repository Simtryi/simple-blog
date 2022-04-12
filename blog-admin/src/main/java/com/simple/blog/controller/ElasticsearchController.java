package com.simple.blog.controller;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.entity.Blog;
import com.simple.blog.mapper.BlogMapper;
import com.simple.blog.search.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Elasticsearch 管理 Controller
 */
@RestController
@RequestMapping("/es")
public class ElasticsearchController {

    @Autowired
    ElasticsearchService elasticsearchService;

    @Autowired
    BlogMapper blogMapper;

    /**
     * Elasticsearch 博客索引
     */
    private static final String ELASTICSEARCH_INDEX_BLOG = "simple-blog-blog";

    /**
     * 索引所有博客
     */
    @PostMapping(value = "/blog/bulk")
    public CommonResult<String> bulkBlog() {
        List<Blog> blogs = blogMapper.findAll();
        String status = elasticsearchService.bulkDocument(ELASTICSEARCH_INDEX_BLOG, blogs);
        return CommonResult.success(status);
    }

    /**
     * 删除博客
     */
    @RequestMapping(value = "/blog/delete/{id}")
    public CommonResult<String> delete(@PathVariable long id) {
        String status = elasticsearchService.deleteDocument(ELASTICSEARCH_INDEX_BLOG, id);
        return CommonResult.success(status);
    }

    /**
     * 更新博客
     */
    @PostMapping(value = "/blog/update")
    public CommonResult<String> update(@RequestBody Blog blog) {
        String status = elasticsearchService.updateDocument(ELASTICSEARCH_INDEX_BLOG, blog);
        return CommonResult.success(status);
    }

    /**
     * 查询博客
     */
    @RequestMapping(value = "/blog/get/{id}")
    public CommonResult<Map<String, Object>> detail(@PathVariable long id) {
        Map<String, Object> blogMap = elasticsearchService.getDocument(ELASTICSEARCH_INDEX_BLOG, id);
        return CommonResult.success(blogMap);
    }

}
