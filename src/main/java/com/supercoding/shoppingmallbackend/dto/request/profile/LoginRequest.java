package com.supercoding.shoppingmallbackend.dto.request.profile;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;
import com.supercoding.shoppingmallbackend.common.util.Regex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ApiModel("로그인 폼")
public class LoginRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_-]+@+[A-Za-z0-9+_.-]+", message = "이메일 형식이 아닙니다.")
    @ApiModelProperty(value = "이메일 구매자계정: minhyeok2@consumer.com  판매자계정: oneho@gmail.com", required = true, example = "minhyeok2@consumer.com")
    private String email;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}", message="숫자, 영어 8자 이상 작성해주세요")
    @ApiModelProperty(value = "비밀번호 구매자비번: minhyeok1234  판매자비번: 1234qwer", required = true, example = "minhyeok1234")
    private String password;

}
