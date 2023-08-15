package com.supercoding.shoppingmallbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "purchase_history")
public class PurchaseHistory extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "consumer_id", nullable = false)
    private Long consumerId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

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

}