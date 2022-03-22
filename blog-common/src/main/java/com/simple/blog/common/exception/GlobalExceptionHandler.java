package com.simple.blog.common.exception;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.common.api.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理自定义 API 异常
     */
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult<Void> handleApiException(ApiException e) {
        if (null != e.getCode()) {
            return CommonResult.failed(e.getCode(), e.getMessage());
        }
        return CommonResult.failed(ResultCode.UNKNOWN, e.getMessage());
    }

    /**
     * 处理参数异常
     */
    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public CommonResult<Void> handleArgumentException(IllegalArgumentException e) {
        return CommonResult.failed(ResultCode.PARAMS_ERROR, e.getMessage());
    }

    /**
     * 处理未知异常
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public CommonResult<Void> handleThrowable(Throwable e) {
        return CommonResult.failed(ResultCode.UNKNOWN, e.getMessage());
    }

}
