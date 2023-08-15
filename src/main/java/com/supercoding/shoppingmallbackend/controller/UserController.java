package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.SignupRequest;
import com.supercoding.shoppingmallbackend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final ProfileService profileService;

    @PostMapping("/singup")
    public CommonResponse<?> signupConsumer(@Valid @RequestBody @ModelAttribute SignupRequest singupRequest,
                                            @RequestParam("profileImage") MultipartFile profileImage){
        profileService.signup(
                singupRequest.getType(),
                singupRequest.getNickname(),
                singupRequest.getPassword(),
                singupRequest.getEmail(),
                singupRequest.getPhoneNumber(),
                null        // TODO: MultipartFile 추가 해줘야 됨
        );

        return CommonResponse.builder().result(true).status(200).message("회원가입에 성공했습니다.").build();
    }

}
