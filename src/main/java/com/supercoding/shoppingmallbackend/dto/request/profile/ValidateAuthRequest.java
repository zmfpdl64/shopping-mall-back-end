package com.supercoding.shoppingmallbackend.dto.request.profile;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ValidateAuthRequest {

    @ApiModelProperty(value = "휴대폰 번호", required = true, example = "010-0000-1031")
    private String phone;
    @ApiModelProperty(value = "인증코드", required = true, example = "123456")
    private String authCode;
}
