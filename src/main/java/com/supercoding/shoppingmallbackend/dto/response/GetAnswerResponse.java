package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.dto.response.questions.GetQuestionResponse;
import com.supercoding.shoppingmallbackend.entity.Answer;
import com.supercoding.shoppingmallbackend.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAnswerResponse {

    private Long answerId;
    private Long questionId;
    private String sellerName;
    private String content;
    private String createAt;

    public static GetAnswerResponse from(Answer answer) {

        return GetAnswerResponse.builder()
                .answerId(answer.getId())
                .questionId(answer.getQuestion().getId())
                .sellerName(answer.getSeller().getProfile().getName())
                .content(answer.getContent())
                .createAt(DateUtils.convertToStringSecond(answer.getCreatedAt()))
                .build();
    }
}
