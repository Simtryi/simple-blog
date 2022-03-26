package com.simple.blog.controller;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.constants.Constants;
import com.simple.blog.entity.User;
import com.simple.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台管理 Controller
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public CommonResult<User> register(@RequestBody User user) {
        User registerUser = adminService.register(user);
        if (null == registerUser) {
            CommonResult.failed();
        }
        return CommonResult.success(registerUser);
    }

    /**
     * 登录
     */
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
        tokenMap.put("authenticationSchema", Constants.JWT_AUTHENTICATION_SCHEMA);
        return CommonResult.success(tokenMap);
    }

    /**
     * 刷新 token
     */
    @RequestMapping("/token/refresh")
    public CommonResult<Map<String, String>> refreshToken(HttpServletRequest request) {
        String authorization = request.getHeader(Constants.JWT_HEADER);
        String token = adminService.refreshToken(authorization);
        if (null == token) {
            return CommonResult.failed(ResultCode.BAD_REQUEST, "token 已过期");
        }

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("authenticationSchema", Constants.JWT_AUTHENTICATION_SCHEMA);
        return CommonResult.success(tokenMap);
    }

    /**
     * 更新密码
     */
    @RequestMapping("/password/update")
    public CommonResult<Void> updatePassword(
            @RequestParam String username,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        adminService.updatePassword(username, oldPassword, newPassword);
        return CommonResult.success();
    }

}
