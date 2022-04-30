package com.cly.backend.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 数据封装。
     * 为什么用泛型？为了能用各种接口生成工具。。
     */
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }
    public static <T> Result<T> success(String msg) {
        return new Result<>(ResultCode.SUCCESS.getCode(), msg, null);
    }
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> failed() {
        return new Result<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg());
    }
    public static <T> Result<T> failed(String msg) {
        return new Result<>(ResultCode.FAILED.getCode(), msg);
    }

    public static <T> Result<T> validateFailed(String msg) {
        return new Result<>(ResultCode.VALIDATE_FAILED.getCode(), msg);
    }

    public static <T> Result<T> unauthorized() {
        return new Result<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg(), null);
    }
    public static <T> Result<T> unauthorized(String msg) {
        return new Result<>(ResultCode.UNAUTHORIZED.getCode(), msg, null);
    }
    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }
}
