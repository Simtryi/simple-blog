package com.simple.blog.controller;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.constants.Constants;
import com.simple.blog.entity.User;
import com.simple.blog.service.AdminService;
import jdk.internal.org.objectweb.asm.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台管理 Controller
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/register")
    public CommonResult<User> register(@RequestBody User user) {
        User registerUser = adminService.register(user);
        if (null == registerUser) {
            CommonResult.failed();
        }
        return CommonResult.success(registerUser);
    }

    @RequestMapping("/login")
    public CommonResult<Map<String, String>> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        String token = adminService.login(username, password);
        if (null == token) {
            return CommonResult.failed(ResultCode.BAD_REQUEST, "用户名或密码错误");
        }

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", Constants.JWT_TOKEN_HEAD);
        return CommonResult.success(tokenMap);
    }

}
