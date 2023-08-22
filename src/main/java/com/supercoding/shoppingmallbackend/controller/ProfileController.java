package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
//<<<<<<< Updated upstream
import com.supercoding.shoppingmallbackend.dto.request.profile.RechargeRequest;
import com.supercoding.shoppingmallbackend.dto.response.profile.ProfileInfoResponse;
//=======
import com.supercoding.shoppingmallbackend.dto.request.profile.*;
//>>>>>>> Stashed changes
import com.supercoding.shoppingmallbackend.dto.response.profile.ProfileMoneyResponse;
import com.supercoding.shoppingmallbackend.dto.response.profile.RechargeResponse;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import com.supercoding.shoppingmallbackend.dto.response.profile.LoginResponse;
import com.supercoding.shoppingmallbackend.service.ProfileService;
import com.supercoding.shoppingmallbackend.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "유저 회원가입, 로그인 API")
@RequestMapping("/api/v1/user")
public class ProfileController {   //TODO: User -> Profile로 명칭 통일 예정

    private final ProfileService profileService;
    private final SmsService smsService;

    @Operation(summary = "회원 가입", description = "구매자, 판매자를 선택하여 회원 가입을 진행합니다.")
    @PostMapping("/signup")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data",
            schema = @Schema(implementation = MultipartFile.class)))
    public CommonResponse<?> signupConsumer(@Validated @ModelAttribute SignupRequest signupRequest,
                                            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage){

        log.info("===================회원가입====================");
        profileService.signup(
                signupRequest.getType(),
                signupRequest.getNickname(),
                signupRequest.getPassword(),
                signupRequest.getEmail(),
                signupRequest.getPhoneNumber(),
                profileImage
        );

        return CommonResponse.success("회원가입에 성공했습니다.", null);
    }

    @Operation(summary = "로그인", description = "회원 이메일과 비밀번호를 입력해 로그인을 진행합니다.")
    @PostMapping("/login")
    public CommonResponse<?> login(@Validated @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse= profileService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return CommonResponse.success("로그인에 성공했습니다", loginResponse);
    }

    @Operation(summary = "남은 요금 조회", description = "토큰을 이용해 유저의 남은 잔액 확인")
    @GetMapping("/recharge")
    public CommonResponse<?> findProfileLeftMoney() {
        Long profileIdx = AuthHolder.getProfileIdx();
        ProfileMoneyResponse profileMoneyResponse = profileService.findProfileLeftMoney(profileIdx);
        return CommonResponse.success("유저의 남은 잔액을 조회했습니다", profileMoneyResponse);
    }

    @Operation(summary = "회원 요금 충전", description = "충전할 요금을 받아 충전합니다 ")
    @PostMapping("/recharge")
    public CommonResponse<?> rechargeProfileMoney(@RequestBody RechargeRequest rechargeRequest) {
        Long profileIdx = AuthHolder.getProfileIdx();
        Long profileTotalMoney = profileService.rechargeProfileMoney(profileIdx, rechargeRequest.getRechargeMoney());
        return CommonResponse.success("충전이 완료됐습니다", new RechargeResponse(profileTotalMoney));
    }

    @Operation(summary = "휴대폰 인증 코드 생성", description = "이메일과 휴대폰번호를 보내면 인증코드를 발송함 ")
    @PostMapping("/sms")
    public CommonResponse<?> generateAuthCode(@RequestBody SmsRequest request) {
        //개발 배포 시 변경 예정
        String authCode = smsService.sendAuthenticationCode(request.getPhoneNum());
        return CommonResponse.success("인증 번호가 전송 됐습니다.", authCode);

    }

    @Operation(summary = "인증 코드 확인", description = "이메일과 인증코드를 보내면 검증 후 임시 비밀번호 반환")
    @PostMapping("/sms/auth")
    public CommonResponse<?> validateAuthCode(@RequestBody ValidateAuthRequest request){
        String authPassword = smsService.authenticationSms(request.getPhoneNum(), request.getAuthCode());
        return CommonResponse.success("인증에 성공했습니다.", authPassword);
    }


    @PostMapping("/profile")
    public CommonResponse<?> changeProfile(@RequestParam("profile") MultipartFile profileImage){
        profileService.changeProfile(profileImage);
        return CommonResponse.success(null, null);
    }

    @Operation(summary = "회원 정보 반환", description = "토큰을 확인하고 유저의 정보를 반환함")
    @GetMapping("/info")
    public CommonResponse<Object> getUserProfile() {
        Long profileIdx = AuthHolder.getProfileIdx();
        ProfileInfoResponse profileInfoResponse = profileService.findProfileInfoByProfileIdx(profileIdx);
        return CommonResponse.success("회원 조회 성공", profileInfoResponse);

    }

}
