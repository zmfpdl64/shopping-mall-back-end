package com.supercoding.shoppingmallbackend.dto.response.questions;

import com.supercoding.shoppingmallbackend.entity.Question;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetQuestionResponse {
    private Long id;
    private Long productIdx;
    private Long consumerIdx;
    private String title;
    private String content;
    private String imageUrl;

    public static GetQuestionResponse from(Question question) {

        return GetQuestionResponse.builder()
                .id(question.getId())
                .consumerIdx(question.getId())
                .productIdx(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .imageUrl(question.getImageUrl())
                .build();
    }
}
