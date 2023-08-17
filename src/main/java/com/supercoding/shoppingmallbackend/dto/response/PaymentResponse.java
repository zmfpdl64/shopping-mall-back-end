package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.dto.request.PaymentRequest;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentResponse {

    private String orderNumber;
    private String paymentAt;
    private String address;
    private String addressDetail;
    private String receiverName;
    private String receiverPhone;
    private List<SimplePurchaseInfoResponse> purchaseInfos;

    public static PaymentResponse from(String orderNumber, PaymentRequest paymentRequest, List<SimplePurchaseInfoResponse> purchaseInfos) {
        return PaymentResponse.builder()
                .orderNumber(orderNumber)
                .paymentAt(DateUtils.convertToString(new Timestamp(new Date().getTime())))
                .address(paymentRequest.getAddress())
                .addressDetail(paymentRequest.getAddressDetail())
                .receiverName(paymentRequest.getReceiverName())
                .receiverPhone(paymentRequest.getReceiverPhone())
                .purchaseInfos(purchaseInfos)
                .build();
    }
}
