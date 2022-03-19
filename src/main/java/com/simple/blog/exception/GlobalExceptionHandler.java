package com.simple.blog.exception;

import com.simple.blog.common.ResultCode;
import com.simple.blog.common.WebResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public WebResult handleApiException(ApiException e) {
        if (null != e.getCode()) {
            return WebResult.failed(e.getCode(), e.getMessage());
        }
        return WebResult.failed(ResultCode.UNKNOWN, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public WebResult handleIllegalArgumentException(IllegalArgumentException e) {
        return WebResult.failed(ResultCode.PARAMS_ERROR, e.getMessage());
    }

}
