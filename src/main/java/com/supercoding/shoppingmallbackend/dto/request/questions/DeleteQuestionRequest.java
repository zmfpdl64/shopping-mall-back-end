package com.supercoding.shoppingmallbackend.dto.request.questions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteQuestionRequest {
    private Long id; // 삭제할 문의의 ID
}
