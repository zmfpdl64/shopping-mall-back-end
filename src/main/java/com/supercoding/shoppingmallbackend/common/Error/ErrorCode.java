package com.supercoding.shoppingmallbackend.common.Error;

public abstract class ErrorCode {

    private final Integer status;
    private final String message;

    protected ErrorCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
