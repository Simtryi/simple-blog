package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.entity.Blog;
import com.simple.blog.mapper.BlogMapper;
import com.simple.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 博客管理 Service 实现类
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Override
    public int create(Blog blog) {
        blog.setCreatedAt(new Date());
        return blogMapper.insert(blog);
    }

    @Override
    public int delete(Long id) {
        return blogMapper.deleteById(id);
    }

    @Override
    public int update(Blog blog) {
        if (null == blog.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填！");
        }

        Blog blogDB = blogMapper.selectById(blog.getId());
        if (null == blogDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + blog.getId() + "对应的博客不存在");
        }

        blog.setUpdatedAt(new Date());
        return blogMapper.update(blog);
    }

    @Override
    public Blog detail(Long id) {
        return blogMapper.selectById(id);
    }

    @Override
    public Page<Blog> list(int pageNum, int pageSize, String title) {
        return blogMapper.page(pageNum, pageSize, title);
    }
    
}