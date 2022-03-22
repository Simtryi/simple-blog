package com.simple.blog.common.exception;

import com.simple.blog.common.api.ResultCode;

/**
 * 自定义 API 异常
 */
public class ApiException extends RuntimeException {

    private ResultCode code;

    public ApiException(ResultCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(ResultCode code, String message) {
        super(message);
        this.code = code;
    }

    public ResultCode getCode() {
        return code;
    }

}
