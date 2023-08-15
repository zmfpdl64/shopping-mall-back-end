package com.supercoding.shoppingmallbackend.dto.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentRequest {
    private Long consumerId;
    private String address;
    private String addressDetail;
    private String receiverName;
    private String receiverPhone;
}
