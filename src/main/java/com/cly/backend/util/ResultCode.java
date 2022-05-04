package com.cly.backend.util;

public enum ResultCode {
    SUCCESS(200, "Success."),
    FAILED(500, "Internal server error for unknown reason. Please contact back-end programmer."),
    VALIDATE_FAILED(404, "Parameters are invalid."),
    UNAUTHORIZED(401, "Fail to authenticate.");

    private final Integer code;
    private final String msg;

    private ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
