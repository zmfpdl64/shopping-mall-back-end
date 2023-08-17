package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.ShoppingCartItemRequest;
import com.supercoding.shoppingmallbackend.dto.response.ShoppingCartItemResponse;
import com.supercoding.shoppingmallbackend.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shoppingcart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    /*
     * ====================================================
     * 아래 필드는 나중에 삭제될 필드
     * ====================================================
     */
    private final boolean isUsingDummyCode = true;

    @PostMapping()
    public CommonResponse<ShoppingCartItemResponse> setProduct(@RequestBody ShoppingCartItemRequest shoppingCartItemRequest) {

        if (isUsingDummyCode) {
            ShoppingCartItemResponse data = ShoppingCartItemResponse.getDummy1();

            return ApiUtils.success("장바구니에 상품을 성공적으로 추가했습니다.", data);
        }

        return shoppingCartService.setProduct(shoppingCartItemRequest);
    };

    @GetMapping()
    public CommonResponse<List<ShoppingCartItemResponse>> getShoppingCart(){

        if (isUsingDummyCode) {
            List<ShoppingCartItemResponse> data = List.of(
                    ShoppingCartItemResponse.getDummy1(),
                    ShoppingCartItemResponse.getDummy2(),
                    ShoppingCartItemResponse.getDummy3()
            );

            return ApiUtils.success("장바구니를 성공적으로 조회했습니다.", data);
        }

        try {
            return shoppingCartService.getShoppingCart();
        } catch(NumberFormatException e) {
            throw new CustomException(CommonErrorCode.INVALID_PATH_VARIABLE);
        }
    }
}
