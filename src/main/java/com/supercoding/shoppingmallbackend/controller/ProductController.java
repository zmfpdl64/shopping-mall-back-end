package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.ProductCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    @Operation(summary = "상품 등록", description = "상품 정보를 입력하여 product 레코드를 생성합니다.")
    @PostMapping(value = "/", consumes = "multipart/form-data")
    public CommonResponse<Object> createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        return ApiUtils.success("상품 등록에 성공하였습니다", null);
    }

}
