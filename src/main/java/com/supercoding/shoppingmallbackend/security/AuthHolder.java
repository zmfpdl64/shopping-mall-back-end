package com.supercoding.shoppingmallbackend.security;

public class AuthHolder {
    private static final ThreadLocal<Long> profileIdxHolder = new ThreadLocal<>();

    public static void setProfileIdx(Long userIdx) { //TODO: porfile로 명칭 변경 예정
        profileIdxHolder.set(userIdx);
    }

    public static Long getProfileIdx() {
        return profileIdxHolder.get();
    }

    public static void clearUserIdx() {
        profileIdxHolder.remove();
    }
}
