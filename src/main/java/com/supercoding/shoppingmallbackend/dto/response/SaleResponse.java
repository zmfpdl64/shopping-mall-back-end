package com.supercoding.shoppingmallbackend.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SoldInfoResponse {
    // 주문번호
    // 배송 주소
    // 판매 일시
    // 거래정보들
    private String orderNumber;
    private String address;
    private String soldDateTime;
    private List<SimplePurchaseInfoResponse> tradeInfos;
}
