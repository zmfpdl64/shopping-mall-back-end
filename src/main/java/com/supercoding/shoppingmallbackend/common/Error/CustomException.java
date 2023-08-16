package com.supercoding.shoppingmallbackend.common.Error;

import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(Integer status, String message) {
        super(message);
        errorCode = new ErrorCode(status, message){};
    }

    public CustomException(HttpStatus status, String message) {
        super(message);
        errorCode = new ErrorCode(status.value(), message){};
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
