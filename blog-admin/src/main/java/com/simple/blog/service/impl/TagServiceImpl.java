package com.simple.blog.service.impl;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.common.util.StringUtil;
import com.simple.blog.entity.Tag;
import com.simple.blog.mapper.TagMapper;
import com.simple.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 标签管理 Service 实现类
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public int create(Tag tag) {
        tag.setCreatedAt(new Date());
        return tagMapper.save(tag);
    }

    @Override
    public int delete(Long id) {
        return tagMapper.deleteById(id);
    }

    @Override
    public int update(Tag tag) {
        if (null == tag.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填");
        }

        Tag tagDB = tagMapper.findById(tag.getId());
        if (null == tagDB) {
            Asserts.fail(ResultCode.NOT_FOUND, StringUtil.format("id={}对应的标签不存在", tag.getId()));
        }

        tag.setUpdatedAt(new Date());
        return tagMapper.update(tag);
    }

    @Override
    public Tag detail(Long id) {
        return tagMapper.findById(id);
    }

    @Override
    public Page<Tag> list(int pageNum, int pageSize, String name) {
        return tagMapper.page(pageNum, pageSize, name);
    }
    
}
