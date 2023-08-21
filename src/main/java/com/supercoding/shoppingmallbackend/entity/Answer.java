package com.supercoding.shoppingmallbackend.entity;

import lombok.*;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @Column(name = "question_idx")
    private Long questionIdx;

    @Column(name = "seller_idx")
    private Long sellerIdx;

    @Lob
    @Column(name = "content")
    private String content;

}