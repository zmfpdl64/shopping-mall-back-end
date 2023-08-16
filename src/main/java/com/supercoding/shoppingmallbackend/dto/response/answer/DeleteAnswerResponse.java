package com.supercoding.shoppingmallbackend.dto.response.answer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteAnswerResponse {
    private Long id;
    private String message;
}
