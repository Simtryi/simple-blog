package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
import com.simple.blog.common.ResultCode;
import com.simple.blog.entity.Blog;
import com.simple.blog.exception.Asserts;
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
    public Blog create(Blog blog) {
        blog.setCreatedAt(new Date());
        blogMapper.insert(blog);
        return blog;
    }

    @Override
    public void delete(Long id) {
        Blog blog = blogMapper.selectById(id);
        if (null != blog) {
            blogMapper.deleteById(id);
        }
    }

    @Override
    public Blog update(Blog blog) {
        if (null == blog.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填！");
        }

        Blog blogDB = blogMapper.selectById(blog.getId());
        if (null == blogDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + blog.getId() + "对应的博客不存在");
        }

        blog.setUpdatedAt(new Date());
        blogMapper.update(blog);
        return blog;
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
