package com.supercoding.shoppingmallbackend.dto.request.profile;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;
import com.supercoding.shoppingmallbackend.common.util.Regex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_-]+@+[A-Za-z0-9+_.-]+", message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,63}", message="숫자, 영어 8자 이상 작성해주세요")
    private String password;

}
