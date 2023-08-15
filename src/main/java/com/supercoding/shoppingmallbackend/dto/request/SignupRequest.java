package com.supercoding.shoppingmallbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class SignupRequest {

    private String type;

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message="패스워드는 필수 항목입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}")
    private String password;

    @NotBlank(message="이름은 필수 항목입니다.")
    private String nickname;

    @NotBlank(message="핸드폰 번호는 필수 항목입니다.")
    @Pattern(regexp = "[0-9]{0,3}-[0-9]{0,4}-[0-9]{0,4}", message = "12~13자리의 -와 숫자를 입력가능합니다")
    private String phoneNumber;
}
