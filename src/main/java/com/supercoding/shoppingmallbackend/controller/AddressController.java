package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.AddressRequest;
import com.supercoding.shoppingmallbackend.dto.response.AddressResponse;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import com.supercoding.shoppingmallbackend.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "배송지 API")
@RequestMapping("/api/v1/user")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "주소 등록및 수정", description = "주소 정보를 입력하여 Address 레코드를 생성및 수정합니다.")
    @PostMapping("/address")
    public CommonResponse<Object> createOrUpdateShippingAddress(AddressRequest addressRequest) {
        Long profileIdx = AuthHolder.getProfileIdx();
        addressService.saveAddress(addressRequest, profileIdx);
        return ApiUtils.success("주소 저장 성공", null);
    }

    @Operation(summary = "주소 조회", description = "토큰을 이용하여 Address 레코드를 조회합니다.")
    @GetMapping("/address")
    public CommonResponse<Object> findAddressByProfileId() {
        Long profileIdx = AuthHolder.getProfileIdx();
        AddressResponse addressResponse = addressService.findAddressByProfileId(profileIdx);
        return ApiUtils.success("주소 조회 성공", addressResponse);
    }

}
