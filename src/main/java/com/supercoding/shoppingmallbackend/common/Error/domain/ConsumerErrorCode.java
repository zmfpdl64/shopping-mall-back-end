package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import org.springframework.http.HttpStatus;

public enum ConsumerErrorCode implements ErrorCodeInterface {
    NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "해당 id의 구매자가 존재하지 않습니다."),
    INVALID_PROFILE_ID(HttpStatus.UNAUTHORIZED, "구매자의 정보가 존재하지 않습니다.");

    private final Integer status;
    private final String message;

    ConsumerErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    ConsumerErrorCode(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message) {};
    }
}
