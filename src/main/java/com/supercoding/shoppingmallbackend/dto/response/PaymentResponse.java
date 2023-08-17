package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.dto.request.PaymentRequest;
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
    private Long purchaseQuantity;
    private Long purchasePrice;
    private Timestamp paidAt;
    private String receivedAddress;
    private String receivedAddressDetail;
    private String recipientName;
    private String recipientPhoneNumber;
}
