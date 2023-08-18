package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.dto.request.PaymentRequest;
import com.supercoding.shoppingmallbackend.dto.response.PaymentResponse;
import com.supercoding.shoppingmallbackend.dto.response.PurchaseResponse;
import com.supercoding.shoppingmallbackend.dto.response.SaleResponse;
import com.supercoding.shoppingmallbackend.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Api(tags = "결제, 결제내역 API")
public class PaymentsController {
    private final PaymentService paymentService;

    @ApiOperation(value = "결제하기", notes = "현재 장바구니에 담긴 모든 상품에 대해 결제를 진행합니다.")
    @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Bearer [JWT Token]", required = true, paramType = "header")
    @PostMapping()
    public CommonResponse<List<PaymentResponse>> processPayment(@RequestBody @ApiParam(name = "결제 요청 객체", value = "배송 받는 사람의 주소, 이름 ,연락처를 알려줄 객체", required = true) PaymentRequest paymentRequest) {
        return paymentService.processPayment(paymentRequest);
    }

    @ApiOperation(value = "구매내역 가져오기", notes = "구매자의 구매내역을 가져옵니다.", response = PurchaseResponse.class, responseContainer = "List")
    @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Bearer [JWT Token]", required = true, paramType = "header")
    @GetMapping("/purchased")
    public CommonResponse<List<PurchaseResponse>> getPurchaseHistory() {
        return paymentService.getPurchaseHistory();
    }

    @ApiOperation(value = "판매내역 가져오기", notes = "판매자의 판매내역을 가져옵니다.", response = SaleResponse.class, responseContainer = "List")
    @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, value = "Bearer [JWT Token]", required = true, paramType = "header")
    @GetMapping("/sold")
    public CommonResponse<List<SaleResponse>> getSaleHistory() {
        return paymentService.getSaleHistory();
    }
}
