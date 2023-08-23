package com.supercoding.shoppingmallbackend.dto.response.profile;

import com.supercoding.shoppingmallbackend.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class LoginResponse {
    @ApiModelProperty(required = true, value = "로그인 이메일", example = "minhyeok@consumer.com")
    private String email;
    @ApiModelProperty(required = true, value = "로그인 토큰", example = "aslkdfl;asdjf;lkasjflkdsjf8931247u9812374djksamfjk")
    private String token;
    @ApiModelProperty(required = true, value = "로그인 역할 CONSUMER | SELLER", example = "CONSUMER")
    private String role;
    @ApiModelProperty(required = true, value = "로그인 프로밀 URL", example = "$2a$10$tOtPkQHjeM0HxjrdGClYoOO.T4XFrhPgvDvRh9KDVdUT35vBP1SyG")
    private String profileUrl;

    public static LoginResponse from(Profile profile, String token){
        log.info("imageProfile: {}", profile.getImageUrl());
        return new LoginResponse(
                profile.getEmail(),
                token,
                profile.getRole().toString(),
                profile.getImageUrl()
        );
    }
}
