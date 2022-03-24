package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.entity.Role;
import com.simple.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理 Controller
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping(value = "/create")
    public CommonResult<Void> create(@RequestBody Role role) {
        int count = roleService.create(role);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        roleService.delete(id);
        return CommonResult.success();
    }

    @PostMapping(value = "/update")
    public CommonResult<Void> update(@RequestBody Role role) {
        int count = roleService.update(role);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/detail/{id}")
    public CommonResult<Role> detail(@PathVariable long id) {
        Role result = roleService.detail(id);
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/list")
    public CommonResult<CommonPage<Role>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name
    ) {
        Page<Role> page = roleService.list(pageNum, pageSize, name);
        return CommonResult.success(new CommonPage<>(page));
    }
    
}
