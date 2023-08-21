package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.dto.response.profile.ProfileResponse;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ConsumerDetailResponse {
    private Long id;
    private ProfileResponse profile;

    public static ConsumerDetailResponse from(Consumer consumer) {
        return ConsumerDetailResponse.builder()
                .id(consumer.getId())
                .profile(ProfileResponse.from(consumer.getProfile()))
                .build();
    }
}
