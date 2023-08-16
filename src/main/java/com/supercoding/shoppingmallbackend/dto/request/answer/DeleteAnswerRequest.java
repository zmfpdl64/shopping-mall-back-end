package com.supercoding.shoppingmallbackend.dto.request.answer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteAnswerRequest {
    private Long id; // 삭제할 답변의 ID
}
