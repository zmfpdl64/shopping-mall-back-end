package com.supercoding.shoppingmallbackend.dto.response;

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
}
