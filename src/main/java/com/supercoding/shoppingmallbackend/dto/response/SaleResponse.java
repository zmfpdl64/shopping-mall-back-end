package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Payment;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SaleResponse {
    private Long paymentId;
    private String orderNumber;
    private ProductSimpleResponse product;
    private Long soldQuantity;
    private Long soldPrice;
    private Timestamp soldAt;
    private String receivedAddress;
    private String receivedAddressDetail;
    private String recipientName;
    private String recipientPhoneNumber;

    public static SaleResponse from(Payment payment) {
        return SaleResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderNumber(payment.getOrderNumber())
                .product(ProductSimpleResponse.from(payment.getProduct()))
                .soldQuantity(payment.getPaidQuantity())
                .soldPrice(payment.getPaidPrice())
                .soldAt(payment.getPaidAt())
                .receivedAddress(payment.getReceivedAddress())
                .receivedAddressDetail(payment.getReceivedAddressDetail())
                .recipientName(payment.getRecipientName())
                .recipientPhoneNumber(payment.getRecipientPhoneNumber())
                .build();
    }
}
