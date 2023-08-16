package com.supercoding.shoppingmallbackend.dto.request.answer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAnswerRequest {
    private Long questionIdx; // 해당 문의의 ID
    private String content;
}
