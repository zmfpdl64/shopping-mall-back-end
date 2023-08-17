package com.supercoding.shoppingmallbackend.common.Error;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {



    @ExceptionHandler(CustomException.class)
    public CommonResponse<?> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.error("\u001B[31mcode: "+errorCode.getStatus()+"\u001B[0m");
        log.error("\u001B[31mmessage: "+ errorCode.getMessage()+"\u001B[0m");
        return CommonResponse.fail(ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e) {
        log.error(e.getMessage(), 500);
        return CommonResponse.fail(e);
    }
}
