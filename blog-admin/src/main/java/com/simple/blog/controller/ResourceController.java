package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.entity.Resource;
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
    ResourceService resourceService;

    @PostMapping(value = "/create")
    public CommonResult<Void> create(@RequestBody Resource resource) {
        int count = resourceService.create(resource);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        resourceService.delete(id);
        return CommonResult.success();
    }

    @PostMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody Resource resource) {
        int count = resourceService.update(resource);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Resource> detail(@PathVariable long id) {
        Resource result = resourceService.detail(id);
        return CommonResult.success(result);
    }

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
