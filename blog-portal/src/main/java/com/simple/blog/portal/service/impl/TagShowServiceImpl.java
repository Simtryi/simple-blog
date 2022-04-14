package com.simple.blog.portal.service.impl;

import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.entity.Tag;
import com.simple.blog.data.mapper.BlogTagRelationMapper;
import com.simple.blog.data.mapper.TagMapper;
import com.simple.blog.portal.service.TagShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 标签展示 Service 实现类
 */
@Service
public class TagShowServiceImpl implements TagShowService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public List<Tag> listAll() {
        return tagMapper.findAll();
    }

    @Override
    public List<Tag> listRandom() {
        List<Tag> tagList = tagMapper.findAll();
        Collections.shuffle(tagList);
        int size = Math.min(10, tagList.size());
        return tagList.subList(0, size);
    }

    @Override
    public List<Blog> getBlogList(Long tagId) {
        return blogTagRelationMapper.findBlogList(tagId);
    }

}
