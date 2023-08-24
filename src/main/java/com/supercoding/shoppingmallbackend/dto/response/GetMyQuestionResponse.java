package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.entity.Answer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyQuestionResponse {

    private Long productId;
    private Long questionId;
    private String productMainImageUrl;
    private String productTitle;
    private String questionTitle;
    private String questionContent;
    private String createAt;

    public static GetMyQuestionResponse from(Question question) {
        return GetMyQuestionResponse.builder()
                .questionId(question.getId())
                .productId(question.getProduct().getId())
                .productMainImageUrl(question.getProduct().getMainImageUrl())
                .productTitle(question.getProduct().getTitle())
                .questionTitle(question.getTitle())
                .questionContent(question.getContent())
                .createAt(DateUtils.convertToStringSecond(question.getCreatedAt()))
                .build();
    }

}
