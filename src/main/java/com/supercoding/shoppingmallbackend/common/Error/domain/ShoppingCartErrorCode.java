package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum ShoppingCartErrorCode implements ErrorCodeInterface {
    EMPTY(HttpStatus.NOT_FOUND, "장바구니가 비어 있습니다."),
    INVALID_PRODUCT(HttpStatus.BAD_REQUEST, "존재하지 않는 상품은 장바구니에 담을 수 없습니다.");

    private final int status;
    private final String message;

    ShoppingCartErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    ShoppingCartErrorCode(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message){};
    }
}
