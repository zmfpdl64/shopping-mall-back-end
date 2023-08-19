package com.supercoding.shoppingmallbackend.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ConsumerDetailResponse {
    private Long id;
    private ProfileResponse profile;
}
