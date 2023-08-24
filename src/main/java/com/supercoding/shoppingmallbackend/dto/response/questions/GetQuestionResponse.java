package com.supercoding.shoppingmallbackend.dto.response.questions;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.dto.response.GetAnswerResponse;
import com.supercoding.shoppingmallbackend.entity.Question;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetQuestionResponse {
    private Long questionId;
    private Long productId;
    private String consumerName;
    private String title;
    private String content;
    private String imageUrl;
    private String createAt;

    private GetAnswerResponse answer;

    public static GetQuestionResponse from(Question question) {

        return GetQuestionResponse.builder()
                .questionId(question.getId())
                .consumerName(question.getConsumer().getProfile().getName())
                .productId(question.getProduct().getId())
                .title(question.getTitle())
                .content(question.getContent())
                .imageUrl(question.getImageUrl())
                .answer(Optional.ofNullable(question.getAnswer())
                        .map(GetAnswerResponse::from)
                        .orElse(null))
                .createAt(DateUtils.convertToStringSecond(question.getCreatedAt()))
                .build();
    }
}
