package com.wyf.designcreate.common;

public class Results {

    public static <T> Result<T> success(T data) {
        return new Result<>(20000, data, "ok");
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<>(errorCode);
    }

    public static <T> Result<T> error(ErrorCode errorCode, String description) {
        return new Result<>(errorCode.getCode(), null, errorCode.getMessage(), description);
    }

    public static <T> Result<T> error(Integer code, String message, String description) {
        return new Result<>(code, null, message, description);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, null, message);
    }

}
