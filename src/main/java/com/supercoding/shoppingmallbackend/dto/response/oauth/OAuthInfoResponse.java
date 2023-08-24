package com.supercoding.shoppingmallbackend.dto.response.oauth;

import com.supercoding.shoppingmallbackend.entity.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}