package com.supercoding.shoppingmallbackend.dto.response.profile;

import com.supercoding.shoppingmallbackend.entity.Profile;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProfileResponse {
    private Long id;
    private String email;
    private String imageUrl;
    private String name;
    private String phoneNumber;
    private Long paymoney;
    private String role;

    public static ProfileResponse from(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .email(profile.getEmail())
                .imageUrl(profile.getImageUrl())
                .name(profile.getName())
                .phoneNumber(profile.getPhone())
                .paymoney(profile.getPaymoney())
                .role(profile.getRole().name())
                .build();
    }
}
