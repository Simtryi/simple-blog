package com.simple.blog.common.exception;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.common.api.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义 API 异常
     */
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult<Void> handleApiException(ApiException e) {
        log.error("业务异常: {}", e.getMessage());
        return CommonResult.failed(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数异常
     */
    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public CommonResult<Void> handleArgumentException(IllegalArgumentException e) {
        log.error("参数异常: {}", e.getMessage());
        return CommonResult.failed(ResultCode.PARAMS_ERROR, e.getMessage());
    }

    /**
     * 处理参数校验异常(普通参数)
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult<Void> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message.append(violation.getMessage()).append(",");
        }
        log.error("参数校验异常：{}", message);
        return CommonResult.failed(ResultCode.PARAMS_ERROR, message.toString());
    }

    /**
     * 处理参数校验异常(对象参数)
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult<Void> handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            message.append(fieldError.getDefaultMessage()).append(",");
        }
        log.error("参数校验异常：{}", message);
        return CommonResult.failed(ResultCode.PARAMS_ERROR, message.toString());
    }

    /**
     * 处理未知异常
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public CommonResult<Void> handleThrowable(Throwable e) {
        log.error("未知异常: {}", e.getMessage());
        return CommonResult.failed(ResultCode.UNKNOWN, e.getMessage());
    }

}
