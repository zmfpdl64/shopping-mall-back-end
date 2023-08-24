package com.supercoding.shoppingmallbackend.dto.response.oauth;

import com.supercoding.shoppingmallbackend.dto.request.oauth.OAuthLoginParams;
import com.supercoding.shoppingmallbackend.entity.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}