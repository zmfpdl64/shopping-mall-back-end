package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.LoginRequest;
import com.supercoding.shoppingmallbackend.dto.request.SignupRequest;
import com.supercoding.shoppingmallbackend.dto.response.LoginResponse;
import com.supercoding.shoppingmallbackend.service.ProfileService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final ProfileService profileService;

    @PostMapping("/singup")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data",
            schema = @Schema(implementation = MultipartFile.class)))
    public CommonResponse<?> signupConsumer(@Valid @ModelAttribute SignupRequest signupRequest,
                                            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage){
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

    @PostMapping("/login")
    public CommonResponse<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse= profileService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return CommonResponse.success("로그인에 성공했습니다", loginResponse);
    }

}
