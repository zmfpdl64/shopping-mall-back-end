package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum GenreErrorCode implements ErrorCodeInterface {
    NOT_FOUND(HttpStatus.NOT_FOUND, "장르를 찾지 못했습니다."),
    NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "해당 id의 장르가 존재하지 않습니다.");

    private Integer status;
    private String message;

    GenreErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    GenreErrorCode(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message){};
    }
}
