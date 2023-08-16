package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.ShoppingCartItemRequest;
import com.supercoding.shoppingmallbackend.dto.response.KoeyConsumerResponse;
import com.supercoding.shoppingmallbackend.dto.response.KoeyProductResponse;
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

    @PostMapping("/set")
    public CommonResponse<ShoppingCartItemResponse> setProduct(@RequestBody ShoppingCartItemRequest shoppingCartItemRequest) {

        //return orderService.setProduct(orderRequest);

        // 더미 코드
        {
            ShoppingCartItemResponse data = ShoppingCartItemResponse.builder()
                    .id(1L)
                    .consumer(KoeyConsumerResponse.builder()
                            .id(1L)
                            .profileId(1L)
                            .build()
                    )
                    .product(KoeyProductResponse.builder()
                            .id(1L)
                            .title("쉐입 퍼즐: 콜로세움 600 PCS")
                            .mainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
                            .amount(99L)
                            .genre("퍼즐")
                            .sellerId(1L)
                            .price(17600L)
                            .build()
                    )
                    .amount(1L)
                    .build();

            return ApiUtils.success("장바구니에 상품을 성공적으로 추가했습니다.", data);
        }
    };

    @GetMapping("/{consumerId}")
    public CommonResponse<Object> getShoppingCart(@PathVariable String consumerId){
//        try {
//            return orderService.getShoppingCart(Long.valueOf(consumerId));
//        } catch(NumberFormatException e) {
//            throw new CustomException(CommonErrorCode.INVALID_PATH_VARIABLE);
//        }
        // 더미 코드
        {
            List<ShoppingCartItemResponse> data = List.of(
                    ShoppingCartItemResponse.builder()
                            .id(1L)
                            .consumer(KoeyConsumerResponse.builder()
                                    .id(Long.valueOf(consumerId))
                                    .profileId(1L)
                                    .build()
                            )
                            .product(KoeyProductResponse.builder()
                                    .id(1L)
                                    .title("쉐입 퍼즐: 콜로세움 600 PCS")
                                    .mainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
                                    .amount(99L)
                                    .genre("퍼즐")
                                    .sellerId(1L)
                                    .price(17600L)
                                    .build()
                            )
                            .amount(1L)
                            .build(),
                    ShoppingCartItemResponse.builder()
                            .id(2L)
                            .consumer(KoeyConsumerResponse.builder()
                                    .id(Long.valueOf(consumerId))
                                    .profileId(1L)
                                    .build()
                            )
                            .product(KoeyProductResponse.builder()
                                    .id(2L)
                                    .title("빨강머리앤 500 두손을 마주잡고")
                                    .mainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg")
                                    .amount(99L)
                                    .genre("퍼즐")
                                    .sellerId(2L)
                                    .price(12000L)
                                    .build()
                            )
                            .amount(2L)
                            .build(),
                    ShoppingCartItemResponse.builder()
                            .id(3L)
                            .consumer(KoeyConsumerResponse.builder()
                                    .id(Long.valueOf(consumerId))
                                    .profileId(1L)
                                    .build()
                            )
                            .product(KoeyProductResponse.builder()
                                    .id(3L)
                                    .title("클루")
                                    .mainImageUrl("https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg")
                                    .amount(99L)
                                    .genre("추리")
                                    .sellerId(3L)
                                    .price(23500L)
                                    .build()
                            )
                            .amount(3L)
                            .build()
            );

            return ApiUtils.success("장바구니를 성공적으로 조회했습니다.", data);
        }
    }
}
