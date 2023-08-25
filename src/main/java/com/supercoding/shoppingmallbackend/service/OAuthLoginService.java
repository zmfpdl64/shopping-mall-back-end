package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.util.RandomUtils;
import com.supercoding.shoppingmallbackend.dto.response.profile.LoginResponse;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Profile;
import com.supercoding.shoppingmallbackend.entity.ProfileRole;
import com.supercoding.shoppingmallbackend.dto.response.oauth.KakaoInfoResponse;
import com.supercoding.shoppingmallbackend.dto.request.oauth.KakaoLoginParams;
import com.supercoding.shoppingmallbackend.dto.response.oauth.OAuthInfoResponse;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.ProfileRepository;
import com.supercoding.shoppingmallbackend.security.JwtUtiles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final ProfileRepository profileRepository;
    private final ConsumerRepository consumerRepository;
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

    @Transactional
    public Profile newProfile(OAuthInfoResponse oAuthInfoResponse) {
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
        profileRepository.save(profile);
        createConsumer(profile);

        return profile;
    }
    private void createConsumer(Profile profile) {
        Consumer consumer = Consumer.builder().profile(profile).build();
        consumerRepository.save(consumer);
    }
}