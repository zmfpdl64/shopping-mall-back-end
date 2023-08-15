package com.supercoding.shoppingmallbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "product_idx", nullable = false)
    private Long productIdx;

    @NotNull
    @Column(name = "consumer_idx", nullable = false)
    private Long consumerIdx;

    @Size(max = 256)
    @NotNull
    @Column(name = "title", nullable = false, length = 256)
    private String title;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

}