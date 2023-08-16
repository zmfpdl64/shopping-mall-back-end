package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode{
    // 유저
    HANDLE_ACCESS_DENIED(403, "로그인이 필요합니다."),
    INVALID_SIGNUP_FILED(400, "가입 정보를 다시 확인 해주세요."),
    INVALID_INPUT_USERNAME(400, "닉네임을 3자 이상 입력하세요"),
    NOTEQUAL_INPUT_PASSWORD(400, "비밀번호가 일치하지 않습니다"),
    INVALID_PASSWORD(400, "비밀번호를 4자 이상 입력하세요"),
    INVALID_USERNAME(400, "알파벳 대소문자와 숫자로만 입력하세요"),
    NOT_AUTHORIZED(403, "작성자만 수정 및 삭제를 할 수 있습니다."),
    USERNAME_DUPLICATION(400, "이미 등록된 아이디입니다."),
    LOGIN_INPUT_INVALID(400, "로그인 정보를 다시 확인해주세요."),
    NOTFOUND_USER(404, "해당 이름의 유저가 존재하지 않습니다.");

    private final ErrorCode errorCode;

    UserErrorCode(Integer status, String message) {
        this.errorCode = new ErrorCode(status, message) {};
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
