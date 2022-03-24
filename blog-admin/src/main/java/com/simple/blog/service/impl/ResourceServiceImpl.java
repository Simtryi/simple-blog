package com.simple.blog.service.impl;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.entity.Resource;
import com.simple.blog.mapper.ResourceMapper;
import com.simple.blog.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 资源管理 Service 实现类
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public int create(Resource resource) {
        resource.setCreatedAt(new Date());
        return resourceMapper.insert(resource);
    }

    @Override
    public int delete(Long id) {
        return resourceMapper.deleteById(id);
    }

    @Override
    public int update(Resource resource) {
        if (null == resource.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填");
        }

        Resource resourceDB = resourceMapper.selectById(resource.getId());
        if (null == resourceDB) {
            Asserts.fail(ResultCode.NOT_FOUND, "id=" + resource.getId() + "对应的资源不存在");
        }

        resource.setUpdatedAt(new Date());
        return resourceMapper.update(resource);
    }

    @Override
    public Resource detail(Long id) {
        return resourceMapper.selectById(id);
    }

    @Override
    public Page<Resource> list(int pageNum, int pageSize, String name, String url) {
        return resourceMapper.page(pageNum, pageSize, name, url);
    }

}
