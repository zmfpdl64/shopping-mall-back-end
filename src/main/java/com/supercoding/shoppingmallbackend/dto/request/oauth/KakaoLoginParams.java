package com.supercoding.shoppingmallbackend.dto.request.oauth;

import com.supercoding.shoppingmallbackend.dto.request.oauth.OAuthLoginParams;
import com.supercoding.shoppingmallbackend.entity.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
    private String code;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        return body;
    }
}