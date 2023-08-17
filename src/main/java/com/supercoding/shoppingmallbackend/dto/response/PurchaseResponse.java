package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Genre;
import com.supercoding.shoppingmallbackend.entity.Payment;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SimplePurchaseInfoResponse {
    private Long paymentId;
    private ProductSimpleResponse product;
    private Long soldAmount;
    private Long soldPricePerOne;

    public static SimplePurchaseInfoResponse from(Payment payment, Genre productGenre) {
        return SimplePurchaseInfoResponse.builder()
                .paymentId(payment.getId())
                .product(ProductSimpleResponse.from(payment.getProduct(), productGenre))
                .soldAmount(payment.getAmount())
                .soldPricePerOne(payment.getSoldPricePerOne())
                .build();
    }
}
