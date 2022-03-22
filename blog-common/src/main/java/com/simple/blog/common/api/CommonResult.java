package com.simple.blog.common.api;

/**
 * 通用返回对象
 */
public class CommonResult<T> {

    /**
     * 状态码
     */
    private ResultCode code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据封装
     */
    private T data;

    protected CommonResult() {}

    protected CommonResult(ResultCode code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ResultCode.OK, ResultCode.OK.getMessage(), null);
    }

    /**
     * 成功
     */
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<T>(ResultCode.OK, message, null);
    }

    /**
     * 成功
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.OK, ResultCode.OK.getMessage(), data);
    }

    /**
     * 失败
     */
    public static <T> CommonResult<T> failed() {
        return new CommonResult<T>(ResultCode.BAD_REQUEST, ResultCode.BAD_REQUEST.getMessage(), null);
    }

    /**
     * 失败
     */
    public static <T> CommonResult<T> failed(ResultCode code) {
        return new CommonResult<T>(code, code.getMessage(), null);
    }

    /**
     * 失败
     */
    public static <T> CommonResult<T> failed(ResultCode code, String message) {
        return new CommonResult<T>(code, message, null);
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
