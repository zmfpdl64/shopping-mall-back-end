package com.supercoding.shoppingmallbackend.dto.response.profile;

import com.supercoding.shoppingmallbackend.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileMoneyResponse {
    @ApiModelProperty(required = true, value = "남은 금액", example = "10000000")
    private Long leftMoney;

    public static ProfileMoneyResponse from(Profile findProfile) {
        return new ProfileMoneyResponse(findProfile.getPaymoney());
    }
}
