package com.supercoding.shoppingmallbackend.dto.request.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RechargeRequest {
    @Range(min = 0, max = 100_000_000)
    private Long rechargeMoney;
}
