package com.supercoding.shoppingmallbackend.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SimpleTradeInfoResponse {
    // 상품 id
    // 상품 이미지
    // 상품 타이틀
    // 구매 개수
    // 지불한 가격
    //
    private Long tradeId;
    private Long productId;
    private String productTitle;
    private String productMainImageUrl;
    private Long soldAmount;
    private Long soldPricePerOne;
}
