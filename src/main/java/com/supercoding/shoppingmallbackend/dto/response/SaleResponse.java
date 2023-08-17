package com.supercoding.shoppingmallbackend.dto.response;

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
}
