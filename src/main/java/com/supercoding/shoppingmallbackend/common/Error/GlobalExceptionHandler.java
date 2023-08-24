package com.supercoding.shoppingmallbackend.common.Error;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UtilErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {



    @ExceptionHandler(BindException.class)
    public CommonResponse<?> handleBindException(BindException be) {

        BindingResult bindingResult = be.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            FieldError fieldError = (FieldError) error;
            errors.put(fieldError.getField(), error.getDefaultMessage());
            log.error("{} : {}", fieldError.getField(), error.getDefaultMessage());
        });
        return CommonResponse.builder()
                .status(400)
                .message(ProfileErrorCode.INVALID_TYPE.getErrorCode().getMessage())
                .data(errors)
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResponse<?> handleMethodNotFoundException(HttpRequestMethodNotSupportedException mne){
        String method = mne.getMethod();
        return CommonResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(method + ": 해당 메소드가 존재하지 않습니다")
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public CommonResponse<?> handleMethodNotFoundException(HttpMediaTypeNotSupportedException mnse){
        return CommonResponse.fail(new CustomException(CommonErrorCode.INVALID_CONTENT_TYPE));
    }


    @ExceptionHandler(CustomException.class)
    public CommonResponse<?> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.error("\u001B[31mcode: "+errorCode.getStatus()+"\u001B[0m");
        log.error("\u001B[31mmessage: "+ errorCode.getMessage()+"\u001B[0m");
        return CommonResponse.fail(ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage(), 500);
        return CommonResponse.fail(new CustomException(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }
}
