package com.supercoding.shoppingmallbackend.dto.request.profile;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.common.util.Regex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
public class SignupRequest {
    @NotBlank(message = "SELLER | CONSUMER 중에 선택해주세요")
    private String type;

    @Pattern(regexp = "^[A-Za-z0-9+_-]+@+[A-Za-z0-9+_.-]+", message = "이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}", message="숫자, 영어 8자 이상 작성해주세요")
    private String password;

    @NotBlank(message="이름은 필수 항목입니다.")
    private String nickname;

    @Pattern(regexp = "[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}", message = "12~13자리의 -와 숫자를 입력가능합니다")
    private String phoneNumber;
}
