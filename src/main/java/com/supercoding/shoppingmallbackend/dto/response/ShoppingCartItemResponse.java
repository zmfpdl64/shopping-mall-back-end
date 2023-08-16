package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ShoppingCartItemResponse {
    private Long id;
    private KoeyConsumerResponse consumer;
    private KoeyProductResponse product;
    private Long amount;

    public static ShoppingCartItemResponse from(ShoppingCart entity) {
        return ShoppingCartItemResponse.builder()
                .id(entity.getId())
                .consumer(KoeyConsumerResponse.from(entity.getConsumer()))
                .product(KoeyProductResponse.from(entity.getProduct()))
                .amount(entity.getAmount())
                .build();
    }
}
