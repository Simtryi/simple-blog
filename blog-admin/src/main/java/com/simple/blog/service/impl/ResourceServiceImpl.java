package com.simple.blog.service.impl;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.common.util.StringUtil;
import com.simple.blog.data.entity.Resource;
import com.simple.blog.data.mapper.ResourceMapper;
import com.simple.blog.data.mapper.RoleResourceRelationMapper;
import com.simple.blog.service.ResourceService;
import com.simple.blog.service.UserCacheService;
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

    @Autowired
    private RoleResourceRelationMapper roleResourceRelationMapper;

    @Autowired
    private UserCacheService userCacheService;

    @Override
    public int create(Resource resource) {
        resource.setCreatedAt(new Date());
        return resourceMapper.save(resource);
    }

    @Override
    public void delete(Long id) {
        //  由于资源删除，删除用户的资源缓存
        userCacheService.delResourceCacheByResourceId(id);

        //  删除角色资源关系
        roleResourceRelationMapper.deleteByResourceId(id);
        //  删除资源
        resourceMapper.deleteById(id);
    }

    @Override
    public int update(Resource resource) {
        if (null == resource.getId()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "编辑时Id必填");
        }

        Resource resourceDB = resourceMapper.findById(resource.getId());
        if (null == resourceDB) {
            Asserts.fail(ResultCode.NOT_FOUND, StringUtil.format("id={}对应的资源不存在", resource.getId()));
        }

        //  由于资源更新，删除用户的资源缓存
        userCacheService.delResourceCacheByResourceId(resource.getId());

        resource.setUpdatedAt(new Date());
        return resourceMapper.update(resource);
    }

    @Override
    public Resource detail(Long id) {
        return resourceMapper.findById(id);
    }

    @Override
    public Page<Resource> list(int pageNum, int pageSize, String name, String url) {
        return resourceMapper.page(pageNum, pageSize, name, url);
    }

}
