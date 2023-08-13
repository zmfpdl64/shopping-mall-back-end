package com.supercoding.shoppingmallbackend.common.Error.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supercoding.shoppingmallbackend.common.Error.ErrorCode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "Invalid Input Value"),
    ENTITY_NOT_FOUND(400, " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    INVALID_TYPE_VALUE(400, " Invalid Type Value");

    private final ErrorCode errorCode;

    CommonErrorCode(Integer status, String message) {
        this.errorCode = new ErrorCode(status, message) {};
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
