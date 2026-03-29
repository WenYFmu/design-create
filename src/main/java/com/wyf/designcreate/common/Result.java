package com.wyf.designcreate.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回类
 *
 */
@Data
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -8881280387543283813L;
    private int code;

    private T data;

    private String message;

    private String description;

    public Result(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public Result(int code, T data, String message) {
        this(code, data, message, "");
    }

    public Result(int code, T data) {
        this(code, data, "", "");
    }

    public Result(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
