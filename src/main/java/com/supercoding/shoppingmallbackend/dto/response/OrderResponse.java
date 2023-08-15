package com.supercoding.shoppingmallbackend.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderResponse {
    private Long id;
    private Object consumer;
    private Object product;
    private Long amount;
}
