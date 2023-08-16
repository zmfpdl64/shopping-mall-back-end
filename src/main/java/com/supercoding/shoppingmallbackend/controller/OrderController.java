package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.OrderRequest;
import com.supercoding.shoppingmallbackend.dto.response.OrderResponse;
import com.supercoding.shoppingmallbackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;

@RestController
@RequestMapping("api/v1/shoppingcart")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/set")
    public CommonResponse<Object> setProduct(@RequestBody OrderRequest orderRequest) {

        return orderService.setProduct(orderRequest);

        // 더미 코드
        {
            OrderResponse data = OrderResponse.builder()
                    .id(1L)
                    .consumer(new Object(){
                        private final Long id = orderRequest.getConsumerId();
                        private final String name = "홍길동";
                    })
                    .product(new Object() {
                        private final Long id = 1L;
                        private final Long sellerId = 1L;
                        private final String title = "쉐입 퍼즐: 콜로세움 600 PCS";
                        private final String mainImageUrl = "https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg";
                        private final String genre = "퍼즐";
                        private final Long price = 17600L;
                    })
                    .amount(1L)
                    .build();

            return ApiUtils.success("장바구니에 상품을 성공적으로 추가했습니다.", data);
        }
    };

    @GetMapping("/{consumerId}")
    public CommonResponse<Object> getShoppingCart(@PathVariable String consumerId){
        try {
            return orderService.getShoppingCart(Long.valueOf(consumerId));
        } catch(NumberFormatException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "consumer_id는 정수로 변환할 수 있는 문자열이어야 합니다.");
        }
        // 더미 코드
        {
            List<OrderResponse> data = List.of(
                    OrderResponse.builder()
                            .id(1L)
                            .consumer(new Object(){
                                private final Long id = Long.valueOf(consumerId);
                                private final String name = "홍길동";
                            })
                            .product(new Object() {
                                private final Long id = 1L;
                                private final Long sellerId = 1L;
                                private final String title = "쉐입 퍼즐: 콜로세움 600 PCS";
                                private final String mainImageUrl = "https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg";
                                private final String genre = "퍼즐";
                                private final Long price = 17600L;
                            })
                            .amount(1L)
                            .build(),
                    OrderResponse.builder()
                            .id(2L)
                            .consumer(new Object(){
                                private final Long id = Long.valueOf(consumerId);
                                private final String name = "홍길동";
                            })
                            .product(new Object() {
                                private final Long id = 2L;
                                private final Long sellerId = 2L;
                                private final String title = "빨강머리앤 500 두손을 마주잡고";
                                private final String mainImageUrl = "https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg";
                                private final String genre = "퍼즐";
                                private final Long price = 12000L;
                            })
                            .amount(2L)
                            .build(),
                    OrderResponse.builder()
                            .id(3L)
                            .consumer(new Object(){
                                private final Long id = Long.valueOf(consumerId);
                                private final String name = "홍길동";
                            })
                            .product(new Object() {
                                private final Long id = 3L;
                                private final Long sellerId = 3L;
                                private final String title = "클루";
                                private final String mainImageUrl = "https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg";
                                private final String genre = "추리";
                                private final Long price = 23500L;
                            })
                            .amount(3L)
                            .build()
            );

            return ApiUtils.success("장바구니를 성공적으로 조회했습니다.", data);
        }
    }
}
