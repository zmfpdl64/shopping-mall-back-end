package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum SellerErrorCode implements ErrorCodeInterface {
    INVALID_PROFILE_ID(HttpStatus.UNAUTHORIZED, "판매자의 정보가 존재하지 않습니다.");

    private final int status;
    private final String message;

    SellerErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
    SellerErrorCode(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message) {
        };
    }
}
