package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCodeInterface;
import org.springframework.http.HttpStatus;

public enum AnswerErrorCode implements ErrorCodeInterface {

    ALREADY_HAVE_ANSWER(HttpStatus.BAD_REQUEST, "이미 답변이 달린 문의입니다.");

    private final Integer status;
    private final String message;

    AnswerErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    AnswerErrorCode(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    @Override
    public ErrorCode getErrorCode() {
        return new ErrorCode(status, message) {};
    }

}
