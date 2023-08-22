package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.dto.request.PaymentRequest;
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
@ApiModel("결제 정보")
public class PaymentResponse {
    @ApiModelProperty(required = true, value = "결제내역 id", example = "1")
    private Long paymentId;
    @ApiModelProperty(required = true, value = "주문번호", example = "23081815a5f")
    private String orderNumber;
    @ApiModelProperty(required = true, value = "상품")
    private ProductSimpleResponse product;
    @ApiModelProperty(required = true, value = "구매 수량", example = "1")
    private Long paidQuantity;
    @ApiModelProperty(required = true, value = "구매 가격", example = "20000")
    private Long paidPrice;
    @ApiModelProperty(required = true, value = "구매 일시", example = "23.08.18")
    private String paidAt;
    @ApiModelProperty(required = true, value = "받는 주소", example = "부산 중구 중구로24번길 22")
    private String receivedAddress;
    @ApiModelProperty(required = true, value = "받는 상세 주소", example = "107호")
    private String receivedAddressDetail;
    @ApiModelProperty(required = true, value = "받는 사람 이름", example = "홍길동")
    private String recipientName;
    @ApiModelProperty(required = true, value = "받는 사람 전화번호", example = "010-1234-5678")
    private String recipientPhoneNumber;

    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderNumber(payment.getOrderNumber())
                .product(ProductSimpleResponse.from(payment.getProduct()))
                .paidQuantity(payment.getPaidQuantity())
                .paidPrice(payment.getPaidPrice())
                .paidAt(DateUtils.convertToString(payment.getPaidAt()))
                .receivedAddress(payment.getReceivedAddress())
                .receivedAddressDetail(payment.getReceivedAddressDetail())
                .recipientName(payment.getRecipientName())
                .recipientPhoneNumber(payment.getRecipientPhoneNumber())
                .build();
    }
}
