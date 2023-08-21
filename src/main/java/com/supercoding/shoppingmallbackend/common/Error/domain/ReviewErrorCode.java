package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum ReviewErrorCode implements ErrorCodeInterface {
    BAD_ID(HttpStatus.BAD_REQUEST, "해당 id의 리뷰가 존재하지 않습니다."),
    INVALID_PRODUCT(HttpStatus.BAD_REQUEST, "존재하지 않는 상품에 대한 리뷰는 조회할 수 없습니다.");

    private int status;
    private String message;

    ReviewErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    ReviewErrorCode(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message){};
    }
}
