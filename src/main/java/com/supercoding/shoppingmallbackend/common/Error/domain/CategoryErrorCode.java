package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum CategoryErrorCode implements ErrorCodeInterface {

    NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "카테고리가 존재하지 않습니다.");

    private final Integer status;
    private final String message;

    CategoryErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    CategoryErrorCode(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message) {};
    }

}
