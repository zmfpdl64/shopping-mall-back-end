package com.supercoding.shoppingmallbackend.common.Error;

import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCodeInterface errorCode) {
        super(errorCode.getErrorCode().getMessage());
        this.errorCode = errorCode.getErrorCode();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
