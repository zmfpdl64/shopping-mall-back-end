package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.ShoppingCartItemRequest;
import com.supercoding.shoppingmallbackend.dto.response.ShoppingCartItemResponse;
import com.supercoding.shoppingmallbackend.service.ShoppingCartService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shoppingcart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping()
    public CommonResponse<ShoppingCartItemResponse> setProduct(@RequestBody ShoppingCartItemRequest shoppingCartItemRequest) {
        return shoppingCartService.setProduct(shoppingCartItemRequest);
    };

    @GetMapping()
    public CommonResponse<List<ShoppingCartItemResponse>> getShoppingCart(){
        try {
            return shoppingCartService.getShoppingCart();
        } catch(NumberFormatException e) {
            throw new CustomException(CommonErrorCode.INVALID_PATH_VARIABLE);
        }
    }
}
