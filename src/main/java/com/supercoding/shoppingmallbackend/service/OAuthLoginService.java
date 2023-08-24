package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.util.RandomUtils;
import com.supercoding.shoppingmallbackend.dto.response.profile.LoginResponse;
import com.supercoding.shoppingmallbackend.entity.Profile;
import com.supercoding.shoppingmallbackend.entity.ProfileRole;
import com.supercoding.shoppingmallbackend.dto.response.oauth.KakaoInfoResponse;
import com.supercoding.shoppingmallbackend.dto.request.oauth.KakaoLoginParams;
import com.supercoding.shoppingmallbackend.dto.response.oauth.OAuthInfoResponse;
import com.supercoding.shoppingmallbackend.repository.ProfileRepository;
import com.supercoding.shoppingmallbackend.security.JwtUtiles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final ProfileRepository profileRepository;
    private final JwtUtiles jwtUtiles;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final BCryptPasswordEncoder encoder;


    public LoginResponse login(KakaoLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Profile profile = findOrCreateProfile(oAuthInfoResponse);
        String token = jwtUtiles.createToken(profile.getId(), profile.getRole().getPosition());
        return LoginResponse.from(profile, token);
    }

    private Profile findOrCreateProfile(OAuthInfoResponse oAuthInfoResponse) {
        return profileRepository.findByEmail(oAuthInfoResponse.getEmail())
                .orElseGet(() -> newProfile(oAuthInfoResponse));
    }

    private Profile newProfile(OAuthInfoResponse oAuthInfoResponse) {
        KakaoInfoResponse kakaoInfoResponse = (KakaoInfoResponse) oAuthInfoResponse;

        Profile profile = Profile.builder()
                .email(oAuthInfoResponse.getEmail())
                .phone(RandomUtils.generateRandomPhone())
                .password(encoder.encode(RandomUtils.generateRandomPassword()))
                .paymoney(0L)
                .imageUrl(kakaoInfoResponse.getKakaoAccount().getProfile().getProfileImageUrl())
                .role(ProfileRole.CONSUMER)
                .name(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return profileRepository.save(profile);
    }
}