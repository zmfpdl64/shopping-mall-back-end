package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum ScrapErrorCode implements ErrorCodeInterface {
    INVALID_PRODUCT(HttpStatus.BAD_REQUEST, "존재하지 않는 상품은 찜하기 할 수 없습니다.");

    private final int status;
    private final String message;

    ScrapErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    ScrapErrorCode(HttpStatus status, String message) {
        this.status = status.value();
        this.message=message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message){};
    }
}
