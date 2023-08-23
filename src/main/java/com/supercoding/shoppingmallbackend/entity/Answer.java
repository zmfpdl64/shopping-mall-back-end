package com.supercoding.shoppingmallbackend.entity;

import com.supercoding.shoppingmallbackend.dto.request.answer.CreateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.request.answer.UpdateAnswerRequest;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SQLDelete(sql = "UPDATE answers as a SET a.is_deleted = true WHERE idx = ?")
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_idx")
    private Question question;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller_idx")
    private Seller seller;

    @Lob
    @Column(name = "content")
    private String content;

    public static Answer from(CreateAnswerRequest createAnswerRequest, Seller seller, Question question) {
        return Answer.builder()
                .question(question)
                .seller(seller)
                .content(createAnswerRequest.getContent())
                .build();

    }

    public static Answer from(Answer originAnswer, UpdateAnswerRequest updateAnswerRequest) {
        return Answer.builder()
                .id(originAnswer.getId())
                .question(originAnswer.getQuestion())
                .seller(originAnswer.getSeller())
                .content(updateAnswerRequest.getContent())
                .build();

    }
}