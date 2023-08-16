package com.supercoding.shoppingmallbackend.common.util;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Status;

public class ApiUtils {

    private ApiUtils(){}

//    public static CommonResponse<Object> success(String message, Object data) {
//        return new CommonResponse<>(true ,200, message, data);
//    }

    public static <T> CommonResponse<T> success(String message, T data) {
        return new CommonResponse<>(true, 200, message, data);
    }

//    public static CommonResponse<Object> fail(int code, String message) {
//        return new CommonResponse<>(false, code, message ,null);
//    }

    public static <T> CommonResponse<T> fail(Integer status, String message) {
        return new CommonResponse<>(false, status, message, null);
    }
}
