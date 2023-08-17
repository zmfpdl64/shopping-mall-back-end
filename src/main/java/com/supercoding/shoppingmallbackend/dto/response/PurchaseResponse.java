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
}