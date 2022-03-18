package com.simple.blog.service.imple;

import com.github.pagehelper.Page;
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
    TagMapper tagMapper;

    @Override
    public Tag create(Tag tag) {
        //  todo 校验
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
            //  todo 抛出异常
        }

        Tag tagDB = tagMapper.selectById(tag.getId());
        if (null == tagDB) {
            //  todo 抛出异常
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
