package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ConsumerResponse {
    private Long id;
    private Long profileId;

    public static ConsumerResponse from(Consumer entity) {
        return ConsumerResponse.builder()
                .id(entity.getId())
                .profileId(entity.getProfile().getId())
                .build();
    }
}
