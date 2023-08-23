package com.supercoding.shoppingmallbackend.dto.request.profile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RechargeRequest {
    @Range(min = 0, max = 100_000_000, message = "0원 이상 1억 이하로 충전이 가능합니다.")
    @ApiModelProperty(value = "충전 금액 0원 이상 1억 이하로 충전이 가능합니다.", required = true, example = "1000")
    private Long rechargeMoney;
}
