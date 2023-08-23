package com.supercoding.shoppingmallbackend.entity;

import com.supercoding.shoppingmallbackend.dto.request.questions.CreateQuestionRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "product_idx", nullable = false)
    private Product product;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "consumer_idx", nullable = false)
    private Consumer consumer;

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

    public static Question from(CreateQuestionRequest createQuestionRequest,Consumer consumer,Product product){
        return Question.builder()
                .consumer(consumer)
                .product(product)
                .title(createQuestionRequest.getTitle())
                .content(createQuestionRequest.getContent())
                .build();
    }

}