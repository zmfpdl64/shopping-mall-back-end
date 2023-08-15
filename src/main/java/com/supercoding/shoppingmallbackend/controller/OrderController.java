package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Status;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.OrderRequest;
import com.supercoding.shoppingmallbackend.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/shoppingcart")
@RequiredArgsConstructor
public class OrderController {

    //private final OrderService orderService;

    @PostMapping("/set")
    public CommonResponse<Object> setProduct(@RequestBody OrderRequest orderRequest) {

        // 더미 코드
        {
            OrderResponse data = OrderResponse.builder()
                    .id(1L)
                    .consumer(new Object(){
                        private final Long id = 1L;
                        private final String name = "홍길동";
                    })
                    .product(new Object() {
                        private final Long id = 1L;
                        private final Long sellerId = 2L;
                        private final String title = "상품 이름";
                        private final String mainImageUrl = "image url1";
                        private final String genre = "sf";
                        private final Long price = 12000L;
                    })
                    .amount(2L)
                    .build();

            return ApiUtils.success("장바구니에 상품을 성공적으로 추가했습니다.", data);
        }
        //return orderService.setProduct(orderRequest);
    };
}
