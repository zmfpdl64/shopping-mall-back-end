package com.supercoding.shoppingmallbackend.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthAndTime {
    private String authCode;
    private String expiredTime;

    public AuthAndTime(String... ins) {
        this.authCode = ins[0];
        this.expiredTime = ins[1];
    }
}
