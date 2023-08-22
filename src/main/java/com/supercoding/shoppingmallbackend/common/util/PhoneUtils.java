package com.supercoding.shoppingmallbackend.common.util;

public class PhoneUtils {
    public static String joinPhoneString(String phoneNum) {
        return phoneNum.replace("-", "");
    }
}
