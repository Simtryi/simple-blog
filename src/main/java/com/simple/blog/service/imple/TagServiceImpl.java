package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
import com.simple.blog.common.ResultCode;
import com.simple.blog.entity.Tag;
import com.simple.blog.exception.Asserts;
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
    TagMapper tagMapper;

    @Override
    public Tag create(Tag tag) {
        tag.setCreatedAt(new Date());
        tagMapper.insert(tag);
        return tag;
    }

    @Override
    public void delete(Long id) {
        Tag tag = tagMapper.selectById(id);
        if (null != tag) {
            tagMapper.deleteById(id);
        }
    }

    @Override
    public Tag update(Tag tag) {
        if (null == tag.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填！");
        }

        Tag tagDB = tagMapper.selectById(tag.getId());
        if (null == tagDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + tag.getId() + "对应的标签不存在");
        }

        tag.setUpdatedAt(new Date());
        tagMapper.update(tag);
        return tag;
    }

    @Override
    public Tag detail(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public Page<Tag> list(int pageNum, int pageSize, String name) {
        return tagMapper.page(pageNum, pageSize, name);
    }
    
}
