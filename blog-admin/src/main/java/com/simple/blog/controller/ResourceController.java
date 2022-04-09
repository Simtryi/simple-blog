package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.entity.Resource;
import com.simple.blog.security.authorization.DynamicSecurityMetadataSource;
import com.simple.blog.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 资源管理 Controller
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    /**
     * 添加资源
     */
    @PostMapping(value = "/create")
    public CommonResult<Void> create(@RequestBody Resource resource) {
        int count = resourceService.create(resource);
        //  当资源发生变化时，清空缓存资源
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除资源
     */
    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        resourceService.delete(id);
        //  当资源发生变化时，清空缓存资源
        dynamicSecurityMetadataSource.clearDataSource();
        return CommonResult.success();
    }

    /**
     * 修改资源
     */
    @PostMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody Resource resource) {
        int count = resourceService.update(resource);
        //  当资源发生变化时，清空缓存资源
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查找资源
     */
    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Resource> detail(@PathVariable long id) {
        Resource result = resourceService.detail(id);
        return CommonResult.success(result);
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/list")
    public CommonResult<CommonPage<Resource>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String url
    ) {
        Page<Resource> page = resourceService.list(pageNum, pageSize, name, url);
        return CommonResult.success(new CommonPage<>(page));
    }
    
}
