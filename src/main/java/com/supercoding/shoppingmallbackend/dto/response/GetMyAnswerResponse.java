package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.entity.Answer;
import com.supercoding.shoppingmallbackend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyAnswerResponse {

    private Long productId;
    private Long answerId;
    private String productMainImageUrl;
    private String productTitle;
    private String answerContent;
    private String createAt;

    public static GetMyAnswerResponse from(Answer answer) {
        return GetMyAnswerResponse.builder()
                .answerId(answer.getId())
                .productId(answer.getQuestion().getProduct().getId())
                .productMainImageUrl(answer.getQuestion().getProduct().getMainImageUrl())
                .productTitle(answer.getQuestion().getProduct().getTitle())
                .answerContent(answer.getContent())
                .createAt(DateUtils.convertToStringSecond(answer.getCreatedAt()))
                .build();
    }

}
