package com.supercoding.shoppingmallbackend.dto.request.profile;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
public class SignupRequest {

    @ApiModelProperty(value = "회원 타입 SELLER | CONSUMER 중에 선택해주세요", required = true, example = "CONSUMER")
    @NotBlank(message = "SELLER | CONSUMER 중에 선택해주세요")
    private String type;

    @ApiModelProperty(value = "이메일 example@email.com", required = true, example = "example@email.com")
    @Pattern(regexp = "^[A-Za-z0-9+_-]+@+[A-Za-z0-9+_.-]+", message = "이메일 형식이 아닙니다.")
    private String email;

    @ApiModelProperty(value = "비밀번호 test1234", required = true, example = "test1234")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}", message="숫자, 영어 8자 이상 작성해주세요")
    private String password;

    @ApiModelProperty(value = "이름 홍길동", required = true, example = "홍길동")
    @NotBlank(message="이름은 필수 항목입니다.")
    private String name;

    @ApiModelProperty(value = "휴대폰 번호 010-1234-1234", required = true, example = "010-1234-1234")
    @Pattern(regexp = "[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}", message = "10~13자리의 -와 숫자를 입력가능합니다")
    private String phone;
}
