package com.supercoding.shoppingmallbackend.dto.request.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePasswordRequest {
    private String email;
    private String password;
    private String updatePassword;
}
