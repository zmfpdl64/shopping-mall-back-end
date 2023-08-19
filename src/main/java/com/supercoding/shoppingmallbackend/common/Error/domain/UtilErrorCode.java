package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum UtilErrorCode implements ErrorCodeInterface {

    INTERNAL_SERVER_ERROR(500, "예상치 못한 에러가 발생했습니다."),
    IOE_ERROR(500, "IOE 읽기 쓰기 에러가 발생했습니다.");
    private final ErrorCode errorCode;

    UtilErrorCode(Integer status, String message) {
        this.errorCode = new ErrorCode(status, message){};
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
