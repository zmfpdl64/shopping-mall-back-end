package com.supercoding.shoppingmallbackend.dto.request.answer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAnswerRequest {
    private String content;
}
