package com.supercoding.shoppingmallbackend.dto.request.profile;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {
    @ApiModelProperty(value = "휴대폰 번호", required = true, example = "010-0000-1031")
    private String phone;
}
