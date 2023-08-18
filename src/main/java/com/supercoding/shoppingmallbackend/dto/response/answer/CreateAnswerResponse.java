package com.supercoding.shoppingmallbackend.dto.response.answer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAnswerResponse {
    private Long id;
    private Long questionIdx;
    private Long sellerIdx;
    private String content;

}
