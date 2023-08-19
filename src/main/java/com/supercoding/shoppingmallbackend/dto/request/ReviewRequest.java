package com.supercoding.shoppingmallbackend.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewRequest {
    private Long productId;
    private String Content;
    private Double rating;
}
