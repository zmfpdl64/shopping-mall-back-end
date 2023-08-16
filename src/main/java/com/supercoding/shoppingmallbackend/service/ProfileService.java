package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UtilErrorCode;
import com.supercoding.shoppingmallbackend.common.util.FilePath;
import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Profile;
import com.supercoding.shoppingmallbackend.entity.ProfileRole;
import com.supercoding.shoppingmallbackend.entity.Seller;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.ProfileRepository;
import com.supercoding.shoppingmallbackend.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.color.ProfileDataException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.supercoding.shoppingmallbackend.common.util.FilePath.MEMBER_PROFILE_DIR;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ConsumerRepository consumerRepository;
    private final SellerRepository sellerRepository;
    private final BCryptPasswordEncoder encoder;
    private final AwsS3Service awsS3Service;


    @Transactional
    public void signup(String type, String nickname, String password, String email, String phoneNumber, MultipartFile profileImage){
        Optional<Profile> findProfile = profileRepository.findByEmail(email);
        if(findProfile.isPresent()){
            throw new CustomException(ProfileErrorCode.DUPLICATE_USER.getErrorCode());
        }
        // 회원 생성 후 Consumer, Seller 생성
        Profile profile = saveProfile(nickname, password, email, phoneNumber);

        try { // 프로필 이미지 S3에 저장
            setProfileImage(profileImage, profile);
        }catch (IOException e){
            throw new CustomException(UtilErrorCode.IOE_ERROR.getErrorCode());
        }
        setProfileRole(type, profile);
    }

    private void setProfileImage(MultipartFile profileImage, Profile profile) throws IOException {
        if(profileImage != null) {
            String url = awsS3Service.upload(profileImage, MEMBER_PROFILE_DIR.getPath() + profile.getId());
            profile.setImageUrl(url);
        }
    }

    private Profile saveProfile(String nickname, String password, String email, String phoneNumber) {
        Profile profile = Profile.builder()
                .email(email)
                .password(encoder.encode(password))
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
