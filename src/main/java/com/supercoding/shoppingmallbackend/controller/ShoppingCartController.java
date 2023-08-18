package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.ShoppingCartItemRequest;
import com.supercoding.shoppingmallbackend.dto.response.ShoppingCartItemResponse;
import com.supercoding.shoppingmallbackend.service.ShoppingCartService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shoppingcart")
@RequiredArgsConstructor
@Api(
        tags = "장바구니 API",
        authorizations = @Authorization(value = "Bearer [JWT Token]")
)
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @ApiOperation(
            value = "장바구니에 상품 세팅(추가 및 수량 변경)",
            notes = "장바구니에 상품을 추가하거나, 상품의 수량을 변경합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = HttpHeaders.AUTHORIZATION,
                    value = "Bearer [JWT Token]",
                    required = true,
                    paramType = "header"
            )
    })
    @PostMapping()
    public CommonResponse<ShoppingCartItemResponse> setProduct(
            @RequestBody
            @ApiParam(
                    required = true,
                    value = "어떤 상품을 얼마나 담았는지 알려줄 객체"
            )
            ShoppingCartItemRequest shoppingCartItemRequest) {
        return shoppingCartService.setProduct(shoppingCartItemRequest);
    };

    @ApiOperation(
            value = "장바구니 전체 조회",
            notes = "장바구니를 전체 조회합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = HttpHeaders.AUTHORIZATION,
                    value = "Bearer [JWT Token]",
                    required = true,
                    paramType = "header"
            )
    })
    @GetMapping()
    public CommonResponse<List<ShoppingCartItemResponse>> getShoppingCart(){
        return shoppingCartService.getShoppingCart();
    }
}
