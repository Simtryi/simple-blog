package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.data.entity.Resource;
import com.simple.blog.data.entity.Role;
import com.simple.blog.data.entity.User;
import com.simple.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户管理 Controller
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加用户
     */
    @PostMapping(value = "/create")
    public CommonResult<User> create(@Valid @RequestBody User user) {
        User registerUser = userService.create(user);
        return CommonResult.success(registerUser);
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete/{id}")
    public CommonResult<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return CommonResult.success();
    }

    /**
     * 修改用户
     */
    @PostMapping(value = "/update")
    public CommonResult<Void> update(@Valid @RequestBody User user) {
        int count = userService.update(user);
        if (count > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查找用户
     */
    @RequestMapping(value = "/detail/{id}")
    public CommonResult<User> detail(@PathVariable long id) {
        User result = userService.detail(id);
        return CommonResult.success(result);
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/list")
    public CommonResult<CommonPage<User>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String email
    ) {
        Page<User> page = userService.list(pageNum, pageSize, username, nickname, email);
        return CommonResult.success(new CommonPage<>(page));
    }

    /**
     * 获取用户的角色列表
     */
    @RequestMapping(value = "/role/list/{id}")
    public CommonResult<List<Role>> getRoleList(@PathVariable long id) {
        List<Role> roleList = userService.getRoleList(id);
        return CommonResult.success(roleList);
    }

    /**
     * 获取用户的资源列表
     */
    @RequestMapping(value = "/resource/list/{id}")
    public CommonResult<List<Resource>> getResourceList(@PathVariable long id) {
        List<Resource> resourceList = userService.getResourceList(id);
        return CommonResult.success(resourceList);
    }

    /**
     * 为用户分配角色
     */
    @PostMapping("/role/assign")
    public CommonResult<Void> assignRole(
            @RequestParam Long userId,
            @RequestParam List<Long> roleIds
    ) {
        userService.assignRole(userId, roleIds);
        return CommonResult.success();
    }

}
