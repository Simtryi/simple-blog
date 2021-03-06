package com.simple.blog.controller;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.constants.Constants;
import com.simple.blog.data.entity.User;
import com.simple.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台管理 Controller
 */
@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public CommonResult<User> register(@Valid @RequestBody User user) {
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
     * 注销
     */
    @RequestMapping("/logout")
    public CommonResult<Void> logout() {
        //  todo
        return CommonResult.success();
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
            @NotBlank(message = "用户名不能为空") @RequestParam String username,
            @NotBlank(message = "原密码不能为空") @RequestParam String oldPassword,
            @NotBlank(message = "新密码不能为空") @RequestParam String newPassword
    ) {
        adminService.updatePassword(username, oldPassword, newPassword);
        return CommonResult.success();
    }

}
