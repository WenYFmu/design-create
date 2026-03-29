package com.wyf.designcreate.exception;

import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.common.Result;
import com.wyf.designcreate.common.Results;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理
 */
@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        log.error("businessException: {}", e.getMessage(), e);
        return Results.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        log.error("runtimeException: {}", e.getMessage(), e);
        return Results.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }


}
