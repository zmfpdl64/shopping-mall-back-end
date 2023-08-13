package com.supercoding.shoppingmallbackend.common.Error;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonResponse<Object> handleException(Exception e) {
        log.error(e.getMessage(), 500);
        return ApiUtils.fail(500, e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public CommonResponse<Object> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.error("code: "+errorCode.getStatus());
        log.error("message: "+ errorCode.getMessage());
        return ApiUtils.fail(errorCode.getStatus(), errorCode.getMessage());
    }
}
