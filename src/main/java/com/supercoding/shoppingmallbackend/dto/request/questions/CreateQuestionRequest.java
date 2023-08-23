package com.supercoding.shoppingmallbackend.dto.request.questions;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateQuestionRequest {
    @NotNull
    private Long productIdx;

    @NotNull
    @Size(max = 256)
    private String title;

    @NotNull
    private String content;

}
