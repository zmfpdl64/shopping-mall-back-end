package com.supercoding.shoppingmallbackend.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("결제 요청 객체")
public class PaymentRequest {
    @ApiModelProperty(value = "받는 주소", required = true, example = "부산 중구 중구로24번길 22")
    private String address;
    @ApiModelProperty(value = "받는 상세 주소", required = true, example = "107호")
    private String addressDetail;
    @ApiModelProperty(value = "받는 사람 이름", required = true, example = "홍길동")
    private String receiverName;
    @ApiModelProperty(value = "받는 사람 전화번호", required = true, example = "010-1234-5678")
    private String receiverPhone;
}
