package com.supercoding.shoppingmallbackend.dto.request;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.common.util.Regex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.regex.Pattern;

@Getter
public class SignupRequest {
    @NotBlank
    private String type;
    @NotBlank
    private String email;
    @NotBlank(message="패스워드는 필수 항목입니다.")
    private String password;
    @NotBlank(message="이름은 필수 항목입니다.")
    private String nickname;
    @NotBlank(message="핸드폰 번호는 필수 항목입니다.")
    private String phoneNumber;

    public SignupRequest(String type, String email, String password, String nickname, String phoneNumber) {
        //입력값 유효성 검사
        if(!Pattern.matches(Regex.EMAIL_PATTERN.getPattern(), email))
            throw new CustomException(ProfileErrorCode.INVALID_EMAIL.getErrorCode());
        else if(!Pattern.matches(Regex.PASSWORD_PATTERN.getPattern(), password))
            throw new CustomException((ProfileErrorCode.INVALID_PASSWORD).getErrorCode());
        else if(!Pattern.matches(Regex.PHONE_PATTERN.getPattern(), phoneNumber))
            throw new CustomException(ProfileErrorCode.INVALID_PHONE.getErrorCode());

        this.type = type;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
}
