package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KoeyConsumerResponse {
    private Long id;
    private Long profileId;

    public static KoeyConsumerResponse from(Consumer entity) {
        return KoeyConsumerResponse.builder()
                .id(entity.getId())
                .profileId(entity.getProfile().getId())
                .build();
    }
}
