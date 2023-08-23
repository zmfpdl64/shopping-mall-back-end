package com.supercoding.shoppingmallbackend.dto.request.profile;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateProfileRequest {
    @ApiModelProperty(value = "이름", required = true, example = "홍길동")
    private String name;
    @ApiModelProperty(value = "핸드폰 번호", required = true, example = "010-1234-1234")
    private String phone;
}
