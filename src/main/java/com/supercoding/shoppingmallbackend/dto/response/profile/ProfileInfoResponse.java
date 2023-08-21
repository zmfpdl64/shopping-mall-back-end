package com.supercoding.shoppingmallbackend.dto.response.profile;

import com.supercoding.shoppingmallbackend.entity.Profile;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("유저 정보 조회")
public class ProfileInfoResponse {

    private Long profileIdx;
    private String email;
    private String name;
    private String phone;
    private String imageUrl;
    private Long payMoney;
    private String role;

    public static ProfileInfoResponse from(Profile profile) {
        return ProfileInfoResponse.builder()
                .profileIdx(profile.getId())
                .email(profile.getEmail())
                .name(profile.getName())
                .phone(profile.getPhone())
                .imageUrl(profile.getImageUrl())
                .payMoney(profile.getPaymoney())
                .role(profile.getRole().name())
                .build();
    }

}
