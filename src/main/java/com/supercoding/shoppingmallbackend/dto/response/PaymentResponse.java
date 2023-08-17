package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.dto.request.PaymentRequest;
import com.supercoding.shoppingmallbackend.entity.Payment;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentResponse {
    private Long paymentId;
    private String orderNumber;
    private ProductSimpleResponse product;
    private Long paidQuantity;
    private Long paidPrice;
    private Timestamp paidAt;
    private String receivedAddress;
    private String receivedAddressDetail;
    private String recipientName;
    private String recipientPhoneNumber;

    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderNumber(payment.getOrderNumber())
                .product(ProductSimpleResponse.from(payment.getProduct()))
                .paidQuantity(payment.getPaidQuantity())
                .paidPrice(payment.getPaidPrice())
                .paidAt(payment.getPaidAt())
                .receivedAddress(payment.getReceivedAddress())
                .receivedAddressDetail(payment.getReceivedAddressDetail())
                .recipientName(payment.getRecipientName())
                .recipientPhoneNumber(payment.getRecipientPhoneNumber())
                .build();
    }
}
