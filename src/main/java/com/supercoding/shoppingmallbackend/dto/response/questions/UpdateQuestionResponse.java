package com.supercoding.shoppingmallbackend.dto.response.questions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateQuestionResponse {
    private Long id;
    private Long productIdx;
    private Long consumerIdx;
    private String title;
    private String content;
    private String imageUrl;
}
