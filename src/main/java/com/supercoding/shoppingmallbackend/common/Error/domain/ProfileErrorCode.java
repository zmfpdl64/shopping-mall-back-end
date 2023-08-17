package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProfileErrorCode {
    INVALID_TYPE(HttpStatus.BAD_REQUEST.value(), "허용하지 않는 권한이 입력됐습니다."),
    DUPLICATE_USER(409, "회원 이메일이 중복됐습니다."),
    INVALID_PASSWORD(400, "숫자, 영어 8자 이상 작성해주세요"),
    INVALID_EMAIL(400, "이메일 형식이 아닙니다."),
    INVALID_PHONE(400, "12~13자리의 -와 숫자를 입력가능합니다");
    private final ErrorCode errorCode;

    ProfileErrorCode(Integer status, String message) {
        this.errorCode = new ErrorCode(status, message){};
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
