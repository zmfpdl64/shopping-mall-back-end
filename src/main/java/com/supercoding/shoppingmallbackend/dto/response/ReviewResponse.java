package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Review;
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

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .consumer(ConsumerDetailResponse.from(review.getConsumer()))
                .product(ProductSimpleResponse.from(review.getProduct()))
                .reviewImageUrl(review.getReviewImageUrl())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }
}
