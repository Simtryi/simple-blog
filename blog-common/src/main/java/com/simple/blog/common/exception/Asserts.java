package com.simple.blog.common.exception;


import com.simple.blog.common.api.ResultCode;

/**
 * 断言处理类，用于抛出各种 API 异常
 */
public class Asserts {

    public static void fail(ResultCode code) {
        throw new ApiException(code);
    }

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(ResultCode code, String message) {
        throw new ApiException(code, message);
    }

}
