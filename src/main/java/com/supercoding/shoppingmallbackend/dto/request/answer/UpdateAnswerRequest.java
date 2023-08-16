package com.supercoding.shoppingmallbackend.dto.request.answer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAnswerRequest {
    private Long id; // 수정할 답변의 ID
    private String content;
}
