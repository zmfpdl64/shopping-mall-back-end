package com.supercoding.shoppingmallbackend.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProfileResponse {
    private Long id;
    private String email;
    private String ImageUrl;
    private String name;
    private String phoneNumber;
    private Long paymoney;
    private String Role;
}
