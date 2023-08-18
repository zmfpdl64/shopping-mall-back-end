package com.supercoding.shoppingmallbackend.dto.request.questions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetQuestionRequest {
    private Long consumerIdx; // 구매자 ID
    private String content;   // 문의 내용 (선택사항)
}
