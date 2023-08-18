package com.supercoding.shoppingmallbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "consumer_idx", nullable = false)
    private Long consumerIdx;

    @NotNull
    @Column(name = "product_idx", nullable = false)
    private Long productIdx;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @Lob
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Double rating;

}