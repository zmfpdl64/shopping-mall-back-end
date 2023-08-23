package com.supercoding.shoppingmallbackend.dto.request.profile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ValidateAuthRequest {

    @ApiModelProperty(value = "휴대폰 번호", required = true, example = "010-0000-1031")
    private String phoneNum;
    @ApiModelProperty(value = "인증코드", required = true, example = "123456")
    private String authCode;
}
