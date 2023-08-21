package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.entity.ProfileRole;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import com.supercoding.shoppingmallbackend.security.JwtUtiles;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Token 복호화, 암호화 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/token")
public class TokenTestController {
    private final JwtUtiles jwtUtiles;

    @Operation(summary = "token 생성", description = "id를 전송하면 token을 생성해 반환함")
    @PostMapping("/encode/{id}")
    public CommonResponse<?> createToken(@PathVariable Long id){
        return CommonResponse.success(jwtUtiles.createToken(id, ProfileRole.CONSUMER.name()), null);
    }

    @Operation(summary = "token 복호화", description = "token을 전송하면 복호화 후 id 반환")
    @GetMapping("/decode/{token}")
    public CommonResponse<?> decodeToken(@RequestParam("token") String token) {
        return CommonResponse.success(null, jwtUtiles.getProfileIdx(token));
    }

    @Operation(summary = "token 복호화", description = "token을 전송했을 때 AuthHolder 객체를 사용해 반환")
    @GetMapping("/use/{token}")
    public CommonResponse<?> useToken() {
        log.info("token: {}", AuthHolder.getProfileIdx());
        return CommonResponse.success(String.valueOf(AuthHolder.getProfileIdx()), null);
    }
}
