package com.supercoding.shoppingmallbackend.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReviewResponse {
    private Long id;
    private ConsumerDetailResponse consumer;
    private ProductSimpleResponse product;
    private String reviewImageUrl;
    private String content;
    private Double rating;
}
