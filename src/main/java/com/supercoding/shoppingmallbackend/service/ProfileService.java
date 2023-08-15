package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Profile;
import com.supercoding.shoppingmallbackend.entity.ProfileRole;
import com.supercoding.shoppingmallbackend.entity.Seller;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.ProfileRepository;
import com.supercoding.shoppingmallbackend.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.color.ProfileDataException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ConsumerRepository consumerRepository;
    private final SellerRepository sellerRepository;
    private final BCryptPasswordEncoder encoder;


    @Transactional
    public void signup(String type, String nickname, String password, String email, String phoneNumber, MultipartFile profileImage) {
        Optional<Profile> findProfile = profileRepository.findByEmail(email);
        if(findProfile.isPresent()){
            throw new CustomException(ProfileErrorCode.DUPLICATE_USER.getErrorCode());
        }
        Profile profile = saveProfile(nickname, password, email, phoneNumber);

        setProfileRole(type, profile);
    }

    private Profile saveProfile(String nickname, String password, String email, String phoneNumber) {
        Profile profile = Profile.builder()
                .email(email)
                .password(encoder.encode(password))
                .imageUrl("")           //TODO: 이미지 저장하고 URL 저장
                .name(nickname)
                .phone(phoneNumber)
                .paymoney(0L)
                .build();
        profile.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        profile.setIsDeleted(false);
        profileRepository.save(profile);
        return profile;
    }

    private void setProfileRole(String type, Profile profile) {
        switch (ProfileRole.getProfileRole(type)) {
            case SELLER:
                createSeller(profile);
                break;
            case CONSUMER:
                createConsumer(profile);
                break;
            default:
                throw new CustomException(ProfileErrorCode.INVALID_TYPE.getErrorCode());
        }
    }

    private void createSeller(Profile profile) {
        Seller seller = Seller.builder().profile_idx(profile.getId()).build(); //TODO: 커밋 전에 수정
        seller.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        seller.setIsDeleted(false);
        profile.setRole(ProfileRole.SELLER);
        sellerRepository.save(seller);
    }
    private void createConsumer(Profile profile) {
        Consumer consumer = Consumer.builder().profileIdx(profile.getId()).build();
        consumer.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        consumer.setIsDeleted(false);
        profile.setRole(ProfileRole.CONSUMER);
        consumerRepository.save(consumer);

    }


}
