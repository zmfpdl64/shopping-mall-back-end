package com.supercoding.shoppingmallbackend.common.util;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Status;

public class ApiUtils {

    private ApiUtils(){}

    public static CommonResponse<Object> success(String message, Object data) {
        Status status = new Status(200, message);
        return new CommonResponse<>(true ,status, data);
    }

    public static CommonResponse<Object> fail(int code, String message) {
        Status status = new Status(code, message);
        return new CommonResponse<>(false, status, null);
    }
}
