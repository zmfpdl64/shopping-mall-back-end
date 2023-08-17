package com.supercoding.shoppingmallbackend.entity;

import com.supercoding.shoppingmallbackend.dto.request.PaymentRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "purchase_history")
public class PurchaseHistory extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "order_number", columnDefinition = "char(11) not null unique")
    private String orderNumber;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Size(max = 127)
    @NotNull
    @Column(name = "address", nullable = false, length = 127)
    private String address;

    @Size(max = 127)
    @NotNull
    @Column(name = "address_detail", nullable = false, length = 127)
    private String addressDetail;

    @Size(max = 63)
    @NotNull
    @Column(name = "receiver_name", nullable = false, length = 63)
    private String receiverName;

    @Size(max = 15)
    @NotNull
    @Column(name="phone", nullable = false, length = 15)
    private String receiverPhone;

    @Size(max = 50)
    @NotNull
    @Column(name = "product_title", nullable = false, length = 50)
    private String productTitle;

    @NotNull
    @Column(name = "sold_price_per_one", nullable = false)
    private Long soldPricePerOne;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public static PurchaseHistory from(String orderNumber, ShoppingCart shoppingCart, PaymentRequest request) {
        Product product = shoppingCart.getProduct();
        return PurchaseHistory.builder()
                .orderNumber(orderNumber)
                .amount(shoppingCart.getAmount())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .productTitle(product.getTitle())
                .soldPricePerOne(product.getPrice())
                .consumer(shoppingCart.getConsumer())
                .product(product)
                .build();
    }
}