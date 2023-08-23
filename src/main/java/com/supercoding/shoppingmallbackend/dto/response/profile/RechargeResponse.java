package com.supercoding.shoppingmallbackend.dto.response.profile;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class RechargeResponse {
    @ApiModelProperty(required = true, value = "남은 금액", example = "10000000")
    private Long rechargeMoney;
}
