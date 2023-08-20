package com.supercoding.shoppingmallbackend.entity;

import com.supercoding.shoppingmallbackend.dto.request.ReviewRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
public class Review extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "consumer_idx")
    private Consumer consumer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_idx")
    private Product product;

    @Lob
    @Column(name = "image_url")
    private String reviewImageUrl;

    @Lob
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Double rating;

    public static Review from(Consumer consumer, Product product, ReviewRequest request) {
        return Review.builder()
                .consumer(consumer)
                .product(product)
                .reviewImageUrl(null)
                .content(request.getContent())
                .rating(request.getRating())
                .build();
    }
}