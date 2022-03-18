package com.simple.blog.common;

/**
 * 通用 Web 返回对象
 */
public class WebResult<T> {

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


    protected WebResult() { }

    protected WebResult(ResultCode code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功
     */
    public static <T> WebResult<T> success() {
        return new WebResult<T>(ResultCode.OK, ResultCode.OK.getMessage(), null);
    }

    /**
     * 成功
     */
    public static <T> WebResult<T> success(String message, T data) {
        return new WebResult<T>(ResultCode.OK, message, data);
    }

    /**
     * 成功
     */
    public static <T> WebResult<T> success(T data) {
        return new WebResult<T>(ResultCode.OK, ResultCode.OK.getMessage(), data);
    }

    /**
     * 失败
     */
    public static <T> WebResult<T> failed(ResultCode code, String message) {
        return new WebResult<T>(code, message, null);
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
