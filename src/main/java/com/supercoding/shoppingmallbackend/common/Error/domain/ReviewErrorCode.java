package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum ReviewErrorCode implements ErrorCodeInterface {
    BAD_ID(HttpStatus.BAD_REQUEST, "해당 id의 리뷰가 존재하지 않습니다.");

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
