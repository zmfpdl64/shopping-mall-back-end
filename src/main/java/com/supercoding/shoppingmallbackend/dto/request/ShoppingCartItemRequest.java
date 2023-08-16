package com.supercoding.shoppingmallbackend.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ShoppingCartItemRequest {
    private Long productId;
    private Long amount;
}
