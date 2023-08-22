package com.supercoding.shoppingmallbackend.security;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;

public class AuthHolder {
    private static final ThreadLocal<Long> profileIdxHolder = new ThreadLocal<>();

    public static void setProfileIdx(Long userIdx) { //TODO: porfile로 명칭 변경 예정
        profileIdxHolder.set(userIdx);
    }

    public static Long getProfileIdx() {
        if(profileIdxHolder.get() == null) throw new CustomException(ProfileErrorCode.INVALID_TOKEN);
        return profileIdxHolder.get();
    }

    public static void clearUserIdx() {
        profileIdxHolder.remove();
    }
}
