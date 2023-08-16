package com.supercoding.shoppingmallbackend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Size(max = 22)
    @NotNull
    @Column(name = "order_number", nullable = false, length = 22)
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
    private String phone;

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
}