package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProfileErrorCode;
import com.supercoding.shoppingmallbackend.common.util.PhoneUtils;
import com.supercoding.shoppingmallbackend.common.util.RandomUtils;
import com.supercoding.shoppingmallbackend.entity.Profile;
import com.supercoding.shoppingmallbackend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {

    @Value("${sms-key}")
    private String smsKey;

    @Value("${sms-secret-key}")
    private String smsSecretKey;

    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder encoder;
    private final Map<String, String> authenticationMap = new HashMap<>();

    /**
     * 임시 코드 생성 메소드
     * @param phoneNum 임시 코드를 전송할 번호 ex) 01012341234
     */
    public String sendAuthenticationCode(String phoneNum){

//        Message coolsms = new Message(smsKey, smsSecretKey);

        String numStr = RandomUtils.generateAuthCode();

        HashMap<String, String> params = new HashMap<>();
        params.put("to", PhoneUtils.joinPhoneString(phoneNum));
        params.put("from", "01021106737");
        params.put("type", "sms");
        params.put("text", "인증번호는 [" + numStr + "] 입니다.");

//        try { //TODO: 실제 서비스 시 주석 해제
//            coolsms.send(params); // 메시지 전송
            String value = numStr + "|" + LocalDateTime.now().toString();
            authenticationMap.put(phoneNum, value);
            log.info("key: {}      value: {}", phoneNum, value);
//        } catch (CoolsmsException e) {
//            throw new CustomException(UtilErrorCode.SEND_ERROR.getErrorCode());
//        }
        return numStr;
    }


    /**
     * 검증 후 임시 비밀번호 반환
     * @param phoneNum 검증 대상이 될 유저의 핸드폰 번호
     * @param authCode 인증 코드
     * @return randomPassword 임시 비밀번호 반환
     */
    public void authenticationSms(String phoneNum, String authCode) {
        // 인증 요청 phoneNum 존재 여부 확인
        if(!authenticationMap.containsKey(phoneNum)){
            throw new CustomException(ProfileErrorCode.NOT_FOUND_PHONE);
        }
        String[] authCodeAndTime = authenticationMap.get(phoneNum).split("\\|");
        String auth = authCodeAndTime[0];
        String time = authCodeAndTime[1];
        // 인증 유효 시간 10분
        if(LocalDateTime.now().isBefore(LocalDateTime.parse(time))) throw new CustomException(ProfileErrorCode.AUTH_TIME_EXPIRED);
        // 인증 코드 일치하는지
        if(!authCode.equals(auth)) throw new CustomException(ProfileErrorCode.NOT_MATCH_VALUE);
        // 인증 key 삭제
        authenticationMap.remove(phoneNum);
    }

    private String setRandomPassword(String phoneNum) {
        // 휴대폰 번호로 찾기
        Profile findProfile = findProfileByPhoneNum(phoneNum);
        // 비밀번호 생성
        String randomPassword = RandomUtils.generateRandomPassword();
        // 임시 비밀번호 저장
        String encodePassword = encoder.encode(randomPassword);
        findProfile.setPassword(encodePassword);
        return randomPassword;
    }

    private Profile findProfileByPhoneNum(String phoneNum) {
        return profileRepository.findByPhoneNum(phoneNum).orElseThrow(() -> new CustomException(ProfileErrorCode.NOT_FOUND));
    }

}
