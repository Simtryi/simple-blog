package com.simple.blog.portal.service.impl;

import com.simple.blog.common.util.MarkdownUtil;
import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.mapper.BlogMapper;
import com.simple.blog.portal.service.BlogShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 博客展示 Service 实现类
 */
@Service
public class BlogShowServiceImpl implements BlogShowService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Blog detail(Long id) {
        Blog blog = blogMapper.findById(id);

        //  todo 浏览量

        //  将 Markdown 格式解析成 HTML 格式
        String html = MarkdownUtil.parse(blog.getContent());
        blog.setContent(html);
        return blog;
    }

}
