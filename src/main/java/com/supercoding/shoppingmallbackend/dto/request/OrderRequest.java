package com.supercoding.shoppingmallbackend.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderRequest {
    private Long consumerId;
    private Long productId;
    private Long amount;
}
