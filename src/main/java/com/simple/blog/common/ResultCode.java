package com.simple.blog.common;

import org.springframework.http.HttpStatus;

/**
 * 状态码
 */
public enum ResultCode {

    OK(HttpStatus.OK, "成功"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "请求不合法"),
    PARAMS_ERROR(HttpStatus.BAD_REQUEST, "请求参数错误"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "内容不存在"),
    LOGIN(HttpStatus.UNAUTHORIZED, "未登录，禁止访问"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "没有权限，禁止访问"),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "服务器未知错误"),
    ;

    private HttpStatus httpStatus;
    private String message;

    private ResultCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

}