package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum OrderByErrorCode implements ErrorCodeInterface {
    INVALID_SORT_PARAMS(HttpStatus.BAD_REQUEST, "정렬 기준 혹은 정렬 방식이 유효하지 않습니다.");

    private final int status;
    private final String message;

    OrderByErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    OrderByErrorCode(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    public CustomException exception() {
        return new CustomException(this);
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message){};
    }
}
