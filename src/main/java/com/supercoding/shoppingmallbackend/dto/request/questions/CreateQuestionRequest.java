package com.supercoding.shoppingmallbackend.dto.request.questions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateQuestionRequest {
    private Long productIdx;
    private Long consumerIdx;
    private String title;
    private String content;
    private String imageUrl;
}
