package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("구매자 간단 정보")
public class ConsumerResponse {
    @ApiModelProperty(required = true, value = "구매자 id", example = "1")
    private Long id;
    @ApiModelProperty(required = true, value = "프로필 id", example = "1")
    private Long profileId;

    public static ConsumerResponse from(Consumer entity) {
        return ConsumerResponse.builder()
                .id(entity.getId())
                .profileId(entity.getProfile().getId())
                .build();
    }
}
