package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Profile;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("구매자의 간단한 정보")
public class ConsumerSimpleResponse {
    private Long id;
    private Long profileId;
    private String name;
    private String imageUrl;

    public static ConsumerSimpleResponse from(Consumer consumer) {
        Profile profile = consumer.getProfile();
        return ConsumerSimpleResponse.builder()
                .id(consumer.getId())
                .profileId(profile.getId())
                .name(profile.getName())
                .imageUrl(profile.getImageUrl())
                .build();
    }
}
