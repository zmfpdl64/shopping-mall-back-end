package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Genre;
import com.supercoding.shoppingmallbackend.entity.Payment;
import lombok.*;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PurchaseResponse {
    private Long paymentId;
    private String orderNumber;
    private ProductSimpleResponse product;
    private Long purchaseQuantity;
    private Long purchasePrice;
    private Timestamp purchasedAt;
    private String receivedAddress;
    private String receivedAddressDetail;
    private String recipientName;
    private String recipientPhoneNumber;

    public static PurchaseResponse from(Payment payment) {
        return PurchaseResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderNumber(payment.getOrderNumber())
                .product(ProductSimpleResponse.from(payment.getProduct()))
                .purchaseQuantity(payment.getPaidQuantity())
                .purchasePrice(payment.getPaidPrice())
                .purchasedAt(payment.getPaidAt())
                .receivedAddress(payment.getReceivedAddress())
                .receivedAddressDetail(payment.getReceivedAddressDetail())
                .recipientName(payment.getRecipientName())
                .recipientPhoneNumber(payment.getRecipientPhoneNumber())
                .build();
    }
}