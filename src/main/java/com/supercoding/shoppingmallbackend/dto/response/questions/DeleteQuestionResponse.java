package com.supercoding.shoppingmallbackend.dto.response.questions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteQuestionResponse {
    private Long id;
    private String message;

}
