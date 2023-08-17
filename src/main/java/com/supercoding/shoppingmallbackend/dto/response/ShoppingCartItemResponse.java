package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ShoppingCartItemResponse {
    private Long id;
    private ConsumerResponse consumer;
    private ProductSimpleResponse product;
    private Long amount;

    public static ShoppingCartItemResponse from(ShoppingCart entity, ProductSimpleResponse productResponse) {
        return ShoppingCartItemResponse.builder()
                .id(entity.getId())
                .consumer(ConsumerResponse.from(entity.getConsumer()))
                .product(productResponse)
                .amount(entity.getAmount())
                .build();
    }

    /*
     * ================================================================================
     * 나중에 삭제될 메서드들
     * ================================================================================
     */

    public static ShoppingCartItemResponse getDummy1() {
        return ShoppingCartItemResponse.builder()
                .id(1L)
                .consumer(ConsumerResponse.builder()
                        .id(1L)
                        .profileId(1L)
                        .build()
                )
                .product(ProductSimpleResponse.builder()
                        .productId(1L)
                        .title("쉐입 퍼즐: 콜로세움 600 PCS")
                        .mainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
//                        .amount(99L)
                        .genre("퍼즐")
                        .sellerId(1L)
                        .price(17600L)
                        .build()
                )
                .amount(2L)
                .build();
    }

    public static ShoppingCartItemResponse getDummy2() {
        return ShoppingCartItemResponse.builder()
                .id(2L)
                .consumer(ConsumerResponse.builder()
                        .id(1L)
                        .profileId(1L)
                        .build()
                )
                .product(ProductSimpleResponse.builder()
                        .productId(2L)
                        .title("빨강머리앤 500 두손을 마주잡고")
                        .mainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg")
//                        .amount(99L)
                        .genre("퍼즐")
                        .sellerId(2L)
                        .price(12000L)
                        .build()
                )
                .amount(2L)
                .build();
    }
    public static ShoppingCartItemResponse getDummy3() {
        return ShoppingCartItemResponse.builder()
                .id(3L)
                .consumer(ConsumerResponse.builder()
                        .id(1L)
                        .profileId(1L)
                        .build()
                )
                .product(ProductSimpleResponse.builder()
                        .productId(3L)
                        .title("클루")
                        .mainImageUrl("https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg")
//                        .amount(99L)
                        .genre("추리")
                        .sellerId(3L)
                        .price(23500L)
                        .build()
                )
                .amount(3L)
                .build();
    }
}
