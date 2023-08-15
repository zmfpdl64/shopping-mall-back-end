package com.supercoding.shoppingmallbackend.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentResponse {
    // 주문번호
    // 결제 날짜
    // 배송 주소
    // 배송 상세주소
    // 받는 사람
    // 받는 사람 연락처
    // 상품들

    private String orderNumber;
    private String paymentDateTime;
    private String address;
    private String addressDetail;
    private String receiverName;
    private String receiverPhone;
    private List<SimpleTradeInfoResponse> tradeInfos = new ArrayList<>();
}
