package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.entity.ProfileRole;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import com.supercoding.shoppingmallbackend.security.JwtUtiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/token")
public class TokenTestController {
    private final JwtUtiles jwtUtiles;

    @PostMapping("/encode/{id}")
    public CommonResponse<?> createToken(@PathVariable Long id){
        return CommonResponse.success(jwtUtiles.createToken(id, ProfileRole.CONSUMER.name()), null);
    }

    @GetMapping("/decode/{token}")
    public CommonResponse<?> decodeToken(@RequestParam("token") String token) {
        return CommonResponse.success(null, jwtUtiles.getProfileIdx(token));
    }

    @GetMapping("/use/{token}")
    public CommonResponse<?> useToken() {
        log.info("token: {}", AuthHolder.getUserIdx());
        return CommonResponse.success(String.valueOf(AuthHolder.getUserIdx()), null);
    }
}
