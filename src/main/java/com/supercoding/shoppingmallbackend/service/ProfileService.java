package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.*;
import com.supercoding.shoppingmallbackend.dto.response.AddressResponse;
import com.supercoding.shoppingmallbackend.dto.response.profile.ProfileInfoResponse;
import com.supercoding.shoppingmallbackend.dto.response.profile.ProfileMoneyResponse;
import com.supercoding.shoppingmallbackend.entity.*;
import com.supercoding.shoppingmallbackend.repository.AddressRepository;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import com.supercoding.shoppingmallbackend.security.JwtUtiles;
import com.supercoding.shoppingmallbackend.dto.vo.ProfileDetail;
import com.supercoding.shoppingmallbackend.dto.response.profile.LoginResponse;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.ProfileRepository;
import com.supercoding.shoppingmallbackend.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.supercoding.shoppingmallbackend.common.util.FilePath.MEMBER_PROFILE_DIR;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ConsumerRepository consumerRepository;
    private final SellerRepository sellerRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder encoder;
    private final AwsS3Service awsS3Service;
    private final JwtUtiles jwtUtiles;


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
        Seller seller = Seller.builder().profile(profile).build();
        profile.setRole(ProfileRole.SELLER);
        sellerRepository.save(seller);
    }
    private void createConsumer(Profile profile) {
        Consumer consumer = Consumer.builder().profile(profile).build();
        profile.setRole(ProfileRole.CONSUMER);
        consumerRepository.save(consumer);
    }


    @Transactional(readOnly = true)
    public LoginResponse login(String email, String password) {
        //유저 존재여부 확인
        Profile profile = getProfileByEmail(email);
        //패스워드 확인
        if(!encoder.matches(password, profile.getPassword())) throw new CustomException(UserErrorCode.LOGIN_INPUT_INVALID.getErrorCode());
        //jwt 토큰 생성
        String token = jwtUtiles.createToken(profile.getId(), profile.getRole().name());
        return LoginResponse.from(profile, token);
    }

    private Profile getProfileByEmail(String email) {
        return profileRepository.findByEmail(email).orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
    }


    @Transactional(readOnly = true)
    public ProfileDetail loadProfileByProfileIdx(Long idx) {

        return ProfileDetail.from(profileRepository.loadProfileByProfileIdx(idx));
    }

    @Transactional(readOnly = true)
    public ProfileMoneyResponse findProfileLeftMoney(Long profileIdx) {
        return ProfileMoneyResponse.from(getFindProfile(profileIdx));
    }

    @Transactional
    public Long rechargeProfileMoney(Long profileIdx, Long rechargeMoney) {

        Profile findProfile = getFindProfile(profileIdx);
        Long profileLeftMoney = findProfile.getPaymoney();
        if(rechargeMoney < 0) {
            throw new CustomException(PaymentErrorCode.INVALID_RECHARGE_VALUE.getErrorCode());
        }
        Long profileTotalMoney = profileLeftMoney + rechargeMoney;
        findProfile.setPaymoney(profileTotalMoney);
        return profileTotalMoney;
    }

    private Profile getFindProfile(Long profileIdx) {
        return profileRepository.findById(profileIdx).orElseThrow(() -> new CustomException(ProfileErrorCode.NOT_FOUND.getErrorCode()));
    }

    // 예시코드
    @Transactional
    public void changeProfile(MultipartFile profileImage) {
        try {
            Profile profile = getFindProfile(AuthHolder.getProfileIdx());
            String updateImageUrl = awsS3Service.updateFile(profileImage, profile.getImageUrl());
            profile.setImageUrl(updateImageUrl);
        } catch (IOException e) {
            throw new CustomException(UtilErrorCode.IOE_ERROR);
        }
    }

    public ProfileInfoResponse findProfileInfoByProfileIdx(Long profileIdx) {

        Long validProfileIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));

        Profile profile = profileRepository.findById(validProfileIdx).orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
        Optional<Address> addressOptional = addressRepository.findByProfile(profile);
        AddressResponse addressResponse = addressOptional.map(AddressResponse::from).orElse(null);
        return ProfileInfoResponse.from(profile, addressResponse);
    }

    @Transactional
    public void deleteProfile(Long profileIdx) {
        Profile profile = getFindProfile(profileIdx);
        removeConsumerOrSeller(profileIdx, profile);
        profile.setIsDeleted(true);
    }

    private void removeConsumerOrSeller(Long profileIdx, Profile profile) {
        switch (profile.getRole()) {
            case CONSUMER:
                Consumer findConsumer = consumerRepository.findByProfileIdAndIsDeletedIsFalse(profileIdx).orElseThrow(() -> new CustomException(ConsumerErrorCode.NOT_FOUND_BY_ID));
                findConsumer.setIsDeleted(true);
                break;
            case SELLER:
                Seller findSeller = sellerRepository.findByProfileIdAndIsDeletedIsFalse(profileIdx).orElseThrow(() -> new CustomException(SellerErrorCode.NOT_FOUND_BY_ID));
                findSeller.setIsDeleted(true);
                break;
            default:
                throw new CustomException(ProfileErrorCode.INVALID_TYPE);
        }
    }

    public void checkDuplicateEmail(String email) {
        if(profileRepository.findByEmail(email).isPresent()) throw new CustomException(ProfileErrorCode.DUPLICATE_USER);
    }

    @Transactional
    public void updatePassword(String email, String password, String updatePassword) {
        Profile findProfile = getProfileByEmail(email);
        if(!encoder.matches(password, findProfile.getPassword())) throw new CustomException(ProfileErrorCode.NOT_FOUND);
        if(findProfile.getOAuthProvider()!=null) throw new CustomException(ProfileErrorCode.UNAUTHORIZED_UPDATE_PASSWORD);
        findProfile.setPassword(encoder.encode(updatePassword));
    }

    @Transactional
    public void changePhoneNums() {
        List<Profile> all = profileRepository.findAll();
        for(int i = 1000; i < all.size() + 1000; i++) {
            Profile profile = all.get(i - 1000);
            String phoneNum = profile.getPhone();
            String updatePhoneNum = phoneNum.substring(0, phoneNum.length()-4) + i;
            profile.setPhone(updatePhoneNum);
        }
    }

    @Transactional
    public void updateProfileInfo(String updateName, String updatePhone) {
        Long profileIdx = AuthHolder.getProfileIdx();
        Profile findProfile = getFindProfile(profileIdx);
        if(updateName!=null) findProfile.setName(updateName);
        if(updatePhone!=null) findProfile.setPhone(updatePhone);
    }
}
