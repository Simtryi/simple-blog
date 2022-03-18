package com.simple.blog.controller;

import com.github.pagehelper.Page;
import com.simple.blog.common.Pager;
import com.simple.blog.common.WebResult;
import com.simple.blog.entity.User;
import com.simple.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/create")
    public WebResult<User> create(@RequestParam User user) {
        User result = userService.create(user);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/delete/{id}")
    public WebResult<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return WebResult.success();
    }

    @PostMapping(value = "/update")
    public WebResult<User> update(@RequestParam User user) {
        User result = userService.update(user);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/detail/{id}")
    public WebResult<User> detail(@PathVariable long id) {
        User result = userService.detail(id);
        return WebResult.success(result);
    }

    @RequestMapping(value = "/list")
    public WebResult<Pager<User>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String email
    ) {
        Page<User> page = userService.list(pageNum, pageSize, username, nickname, email);
        return WebResult.success(new Pager<>(page));
    }

}
