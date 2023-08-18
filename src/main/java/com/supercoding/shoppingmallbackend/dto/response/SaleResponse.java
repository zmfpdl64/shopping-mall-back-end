package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Payment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("판매내역")
public class SaleResponse {
    @ApiModelProperty(required = true, value = "결제내역 id", example = "1")
    private Long paymentId;
    @ApiModelProperty(required = true, value = "주문번호", example = "23081815a5f")
    private String orderNumber;
    @ApiModelProperty(required = true, value = "상품")
    private ProductSimpleResponse product;
    @ApiModelProperty(required = true, value = "판매 수량", example = "1")
    private Long soldQuantity;
    @ApiModelProperty(required = true, value = "판매 가격", example = "20000")
    private Long soldPrice;
    @ApiModelProperty(required = true, value = "판매 가격", example = "23.08.18")
    private Timestamp soldAt;
    @ApiModelProperty(required = true, value = "받는 주소", example = "부산 중구 중구로24번길 22")
    private String receivedAddress;
    @ApiModelProperty(required = true, value = "받는 상세 주소", example = "107호")
    private String receivedAddressDetail;
    @ApiModelProperty(required = true, value = "받는 사람 이름", example = "홍길동")
    private String recipientName;
    @ApiModelProperty(required = true, value = "받는 사람 전화번호", example = "010-1234-5678")
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
