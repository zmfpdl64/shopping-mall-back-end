package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.dto.request.PaymentRequest;
import com.supercoding.shoppingmallbackend.dto.response.PaymentResponse;
import com.supercoding.shoppingmallbackend.dto.response.PurchaseResponse;
import com.supercoding.shoppingmallbackend.dto.response.SaleResponse;
import com.supercoding.shoppingmallbackend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentsController {
    private final PaymentService paymentService;
    private final boolean isUsingDummyCode = true;    // 나중에 더미코드들 다 걷어냄

    @PostMapping()
    public CommonResponse<List<PaymentResponse>> processPayment(@RequestBody PaymentRequest paymentRequest) {

//        if (isUsingDummyCode) {
//            PaymentResponse data = PaymentResponse.builder()
//                    .orderNumber("123456789")
//                    .address("부산 중구 중구로24번길 22")
//                    .addressDetail("1층 107호")
//                    .paymentDateTime("2023.08.15 22:27")
//                    .receiverName("홍길동")
//                    .receiverPhone("010-1234-5678")
//                    .build();
//            data.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(1L)
//                            .productId(1L)
//                            .productTitle("쉐입 퍼즐: 콜로세움 600 PCS")
//                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
//                            .soldAmount(1L)
//                            .soldPricePerOne(18000L)
//                            .build()
//            );
//            data.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(2L)
//                            .productId(2L)
//                            .productTitle("빨강머리앤 500 두손을 마주잡고")
//                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg")
//                            .soldAmount(2L)
//                            .soldPricePerOne(11900L)
//                            .build()
//            );
//            data.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(3L)
//                            .productId(3L)
//                            .productTitle("클루")
//                            .productMainImageUrl("https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg")
//                            .soldAmount(3L)
//                            .soldPricePerOne(23490L)
//                            .build()
//            );
//
//            return ApiUtils.success("결제를 성공적으로 완료했습니다.", data);
//        }

        return paymentService.processPayment(paymentRequest);
    }

    @GetMapping("/purchased")
    public CommonResponse<List<PurchaseResponse>> getPurchaseHistory() {

//        if (isUsingDummyCode) {
//            PaymentResponse data = PaymentResponse.builder()
//                    .orderNumber("123456789")
//                    .address("부산 중구 중구로24번길 22")
//                    .addressDetail("1층 107호")
//                    .paymentAt("2023.08.15 22:27")
//                    .receiverName("홍길동")
//                    .receiverPhone("010-1234-5678")
//                    .build();
//            data.getPurchaseInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .paymentId(1L)
//                            .product(1L)
//                            .productTitle("쉐입 퍼즐: 콜로세움 600 PCS")
//                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
//                            .soldAmount(1L)
//                            .soldPricePerOne(18000L)
//                            .build()
//            );
//            data.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(2L)
//                            .productId(2L)
//                            .productTitle("빨강머리앤 500 두손을 마주잡고")
//                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg")
//                            .soldAmount(2L)
//                            .soldPricePerOne(11900L)
//                            .build()
//            );
//            data.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(3L)
//                            .productId(3L)
//                            .productTitle("클루")
//                            .productMainImageUrl("https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg")
//                            .soldAmount(3L)
//                            .soldPricePerOne(23490L)
//                            .build()
//            );
//
//            PaymentResponse data1 = PaymentResponse.builder()
//                    .orderNumber("987654321")
//                    .address("부산 동구 중앙대로214번길 7-8")
//                    .addressDetail("1406호")
//                    .paymentDateTime("2023.08.15 22:36")
//                    .receiverName("유관순")
//                    .receiverPhone("010-1478-9632")
//                    .build();
//            data1.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(4L)
//                            .productId(1L)
//                            .productTitle("쉐입 퍼즐: 콜로세움 600 PCS")
//                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
//                            .soldAmount(1L)
//                            .soldPricePerOne(18000L)
//                            .build()
//            );
//            data1.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(5L)
//                            .productId(2L)
//                            .productTitle("빨강머리앤 500 두손을 마주잡고")
//                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg")
//                            .soldAmount(2L)
//                            .soldPricePerOne(11900L)
//                            .build()
//            );
//            data1.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(6L)
//                            .productId(3L)
//                            .productTitle("클루")
//                            .productMainImageUrl("https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg")
//                            .soldAmount(3L)
//                            .soldPricePerOne(23490L)
//                            .build()
//            );
//
//            PaymentResponse data2 = PaymentResponse.builder()
//                    .orderNumber("789654123")
//                    .address("부산 동구 중앙대로296번길 6")
//                    .addressDetail("1102호")
//                    .paymentDateTime("2023.08.15 22:38")
//                    .receiverName("이순신")
//                    .receiverPhone("010-5478-5632")
//                    .build();
//            data2.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(7L)
//                            .productId(1L)
//                            .productTitle("쉐입 퍼즐: 콜로세움 600 PCS")
//                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
//                            .soldAmount(1L)
//                            .soldPricePerOne(18000L)
//                            .build()
//            );
//            data2.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(8L)
//                            .productId(2L)
//                            .productTitle("빨강머리앤 500 두손을 마주잡고")
//                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg")
//                            .soldAmount(2L)
//                            .soldPricePerOne(11900L)
//                            .build()
//            );
//            data2.getTradeInfos().add(
//                    SimplePurchaseInfoResponse.builder()
//                            .tradeId(9L)
//                            .productId(3L)
//                            .productTitle("클루")
//                            .productMainImageUrl("https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg")
//                            .soldAmount(3L)
//                            .soldPricePerOne(23490L)
//                            .build()
//            );
//
//            return ApiUtils.success("구매내역을 성공적으로 조회했습니다.", List.of(data, data1, data2));
//        }

        return paymentService.getPurchaseHistory();
    }

    @GetMapping("/sold")
    public CommonResponse<List<SaleResponse>> getSaleHistory() {

//        if (isUsingDummyCode) {
//            List<SoldInfoResponse> data = List.of(
//                    SoldInfoResponse.builder()
//                            .orderNumber("123456789")
//                            .address("부산 중구 중구로24번길 22")
//                            .soldDateTime("2023.08.15 22:27")
//                            .tradeInfos(List.of(
//                                    SimplePurchaseInfoResponse.builder()
//                                            .tradeId(1L)
//                                            .productId(1L)
//                                            .productTitle("쉐입 퍼즐: 콜로세움 600 PCS")
//                                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
//                                            .soldAmount(1L)
//                                            .soldPricePerOne(18000L)
//                                            .build(),
//                                    SimplePurchaseInfoResponse.builder()
//                                            .tradeId(2L)
//                                            .productId(2L)
//                                            .productTitle("빨강머리앤 500 두손을 마주잡고")
//                                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg")
//                                            .soldAmount(2L)
//                                            .soldPricePerOne(11900L)
//                                            .build(),
//                                    SimplePurchaseInfoResponse.builder()
//                                            .tradeId(3L)
//                                            .productId(3L)
//                                            .productTitle("클루")
//                                            .productMainImageUrl("https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg")
//                                            .soldAmount(3L)
//                                            .soldPricePerOne(23490L)
//                                            .build()
//                            ))
//                            .build(),
//                    SoldInfoResponse.builder()
//                            .orderNumber("987654321")
//                            .address("부산 동구 중앙대로214번길 7-8")
//                            .soldDateTime("2023.08.15 22:36")
//                            .tradeInfos(List.of(
//                                    SimplePurchaseInfoResponse.builder()
//                                            .tradeId(4L)
//                                            .productId(1L)
//                                            .productTitle("쉐입 퍼즐: 콜로세움 600 PCS")
//                                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
//                                            .soldAmount(1L)
//                                            .soldPricePerOne(18000L)
//                                            .build(),
//                                    SimplePurchaseInfoResponse.builder()
//                                            .tradeId(5L)
//                                            .productId(2L)
//                                            .productTitle("빨강머리앤 500 두손을 마주잡고")
//                                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg")
//                                            .soldAmount(2L)
//                                            .soldPricePerOne(11900L)
//                                            .build(),
//                                    SimplePurchaseInfoResponse.builder()
//                                            .tradeId(6L)
//                                            .productId(3L)
//                                            .productTitle("클루")
//                                            .productMainImageUrl("https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg")
//                                            .soldAmount(3L)
//                                            .soldPricePerOne(23490L)
//                                            .build()
//                            ))
//                            .build(),
//                    SoldInfoResponse.builder()
//                            .orderNumber("789654123")
//                            .address("부산 동구 중앙대로296번길 6")
//                            .soldDateTime("2023.08.15 22:38")
//                            .tradeInfos(List.of(
//                                    SimplePurchaseInfoResponse.builder()
//                                            .tradeId(7L)
//                                            .productId(1L)
//                                            .productTitle("쉐입 퍼즐: 콜로세움 600 PCS")
//                                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
//                                            .soldAmount(1L)
//                                            .soldPricePerOne(18000L)
//                                            .build(),
//                                    SimplePurchaseInfoResponse.builder()
//                                            .tradeId(8L)
//                                            .productId(2L)
//                                            .productTitle("빨강머리앤 500 두손을 마주잡고")
//                                            .productMainImageUrl("https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1684916549_0.jpg")
//                                            .soldAmount(2L)
//                                            .soldPricePerOne(11900L)
//                                            .build(),
//                                    SimplePurchaseInfoResponse.builder()
//                                            .tradeId(9L)
//                                            .productId(3L)
//                                            .productTitle("클루")
//                                            .productMainImageUrl("https://thumbnail10.coupangcdn.com/thumbnails/remote/230x230ex/image/product/image/vendoritem/2018/08/23/3007143242/8be8c2f6-5880-4a23-9a0b-260e503a4a1b.jpg")
//                                            .soldAmount(3L)
//                                            .soldPricePerOne(23490L)
//                                            .build()
//                            ))
//                            .build()
//            );
//
//            return ApiUtils.success("판매내역을 성공적으로 조회했습니다.", data);
//        }
        return paymentService.getSaleHistory();
    }
}
