package com.supercoding.shoppingmallbackend.dto.request.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ValidateAuthRequest {
    private String phoneNum;
    private String authCode;
}
