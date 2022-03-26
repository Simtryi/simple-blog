package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.entity.Resource;
import com.simple.blog.entity.Role;
import com.simple.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理 Controller
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加角色
     */
    @PostMapping(value = "/create")
    public CommonResult<Void> create(@RequestBody Role role) {
        int count = roleService.create(role);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        roleService.delete(id);
        return CommonResult.success();
    }

    /**
     * 修改角色
     */
    @PostMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody Role role) {
        int count = roleService.update(role);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查找角色
     */
    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Role> detail(@PathVariable long id) {
        Role result = roleService.detail(id);
        return CommonResult.success(result);
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/list")
    public CommonResult<CommonPage<Role>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name
    ) {
        Page<Role> page = roleService.list(pageNum, pageSize, name);
        return CommonResult.success(new CommonPage<>(page));
    }

    /**
     * 获取角色的资源列表
     */
    @RequestMapping(value = "/resource/list/{id}")
    public CommonResult<List<Resource>> getResourceList(@PathVariable long id) {
        List<Resource> resourceList = roleService.getResourceList(id);
        return CommonResult.success(resourceList);
    }

    /**
     * 为角色分配资源
     */
    @PostMapping(value = "/resource/assign")
    public CommonResult<Void> assignResource(
            @RequestParam Long roleId,
            @RequestParam List<Long> resourceIds
    ) {
        roleService.assignResource(roleId, resourceIds);
        return CommonResult.success();
    }

}
