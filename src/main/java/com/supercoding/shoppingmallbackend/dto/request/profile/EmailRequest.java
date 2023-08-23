package com.supercoding.shoppingmallbackend.dto.request.profile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@ApiModel("이메일 중복 확인 객체")
@Getter
public class EmailRequest {
    @ApiModelProperty(value = "이메일", required = true, example = "example@email.com")
    private String email;
}
