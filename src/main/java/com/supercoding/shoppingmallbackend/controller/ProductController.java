package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.ProductCreateRequest;
import com.supercoding.shoppingmallbackend.dto.response.ProductDetailResponse;
import com.supercoding.shoppingmallbackend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "상품 정보를 입력하여 product 레코드를 생성합니다.")
    @PostMapping(value = "/", consumes = "multipart/form-data")
    public CommonResponse<Object> createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        productService.createProductItem(productCreateRequest);

        return ApiUtils.success("상품 등록 성공", null);
    }

    @Operation(summary = "상품 조회", description = "상품 식별값을 입력하여 단일의 product 레코드를 조회합니다.")
    @PostMapping(value = "/{product_idx}")
    public CommonResponse<Object> getProduct(@PathVariable("product_idx") Long productId) {

        ProductDetailResponse productDetailResponse = productService.getProductByProductId(productId);

        return ApiUtils.success("상품 조회 성공", productDetailResponse);
    }

    @Operation(summary = "상품 삭제", description = "상품 식별값을 입력하여 단일의 product 레코드를 삭제합니다.")
    @DeleteMapping("/{product_idx}")
    public CommonResponse<Object> deleteProduct(@PathVariable("product_idx") Long productId) {
        return ApiUtils.success(productId + "번 상품 삭제 성공", null);
    }

    @Operation(summary = "상품 수정", description = "상품 식별값을 입력하여 단일의 product 레코드를 수정합니다.")
    @PatchMapping("/{product_idx}")
    public CommonResponse<Object> updateProduct(@PathVariable("product_idx") Long productId) {
        return ApiUtils.success(productId+ "번 상품 수정 성공", null);
    }


}
