package com.supercoding.shoppingmallbackend.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private final boolean result;
    private final Integer status;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    @Builder
    public CommonResponse(boolean result, Integer status, String message,  T data) {
        this.result = result;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}


