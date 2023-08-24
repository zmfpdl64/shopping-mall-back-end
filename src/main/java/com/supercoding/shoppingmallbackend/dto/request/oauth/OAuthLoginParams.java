package com.supercoding.shoppingmallbackend.dto.request.oauth;

import com.supercoding.shoppingmallbackend.entity.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}