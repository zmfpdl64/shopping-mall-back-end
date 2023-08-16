package com.supercoding.shoppingmallbackend.dto.request.questions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateQuestionRequest {
    private Long id; // 수정할 문의의 ID
    private String title;
    private String content;
    private String imageUrl;
}
