package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Genre;
import com.supercoding.shoppingmallbackend.entity.PurchaseHistory;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SimplePurchaseInfoResponse {
    // 상품 id
    // 상품 이미지
    // 상품 타이틀
    // 구매 개수
    // 지불한 가격
    //
    private Long paymentId;
    private ProductSimpleResponse product;
    private Long soldAmount;
    private Long soldPricePerOne;

    public static SimplePurchaseInfoResponse from(PurchaseHistory purchaseHistory, Genre productGenre) {
        return SimplePurchaseInfoResponse.builder()
                .paymentId(purchaseHistory.getId())
                .product(ProductSimpleResponse.from(purchaseHistory.getProduct(), productGenre))
                .soldAmount(purchaseHistory.getAmount())
                .soldPricePerOne(purchaseHistory.getSoldPricePerOne())
                .build();
    }
}
