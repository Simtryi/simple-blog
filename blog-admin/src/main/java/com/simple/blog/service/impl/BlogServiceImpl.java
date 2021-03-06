package com.simple.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.common.util.StringUtil;
import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.entity.BlogTagRelation;
import com.simple.blog.data.mapper.BlogMapper;
import com.simple.blog.data.mapper.BlogTagRelationMapper;
import com.simple.blog.service.BlogService;
import org.ehcache.core.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客管理 Service 实现类
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public int create(Blog blog) {
        blog.setCreatedAt(new Date());
        return blogMapper.save(blog);
    }

    @Override
    public int delete(Long id) {
        //  删除博客标签关系
        blogTagRelationMapper.deleteByBlogId(id);
        //  删除博客
        return blogMapper.deleteById(id);
    }

    @Override
    public int update(Blog blog) {
        if (null == blog.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填");
        }

        Blog blogDB = blogMapper.findById(blog.getId());
        if (null == blogDB) {
            Asserts.fail(ResultCode.NOT_FOUND, StringUtil.format("id={}对应的博客不存在", blog.getId()));
        }

        blog.setUpdatedAt(new Date());
        return blogMapper.update(blog);
    }

    @Override
    public Blog detail(Long id) {
        return blogMapper.findById(id);
    }

    @Override
    public Page<Blog> list(int pageNum, int pageSize, String title) {
        return blogMapper.page(pageNum, pageSize, title);
    }

    @Override
    public void assignTag(Long blogId, List<Long> tagIds) {
        //  删除原有关系
        blogTagRelationMapper.deleteByBlogId(blogId);

        //  建立新关系
        if (!CollUtil.isEmpty(tagIds)) {
            List<BlogTagRelation> list = new ArrayList<>();
            tagIds.forEach(tagId -> {
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setBlogId(blogId);
                blogTagRelation.setTagId(tagId);
                list.add(blogTagRelation);
            });
            blogTagRelationMapper.saveAll(list);
        }
    }

}
