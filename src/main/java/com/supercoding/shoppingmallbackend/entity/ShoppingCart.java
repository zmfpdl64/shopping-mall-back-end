package com.supercoding.shoppingmallbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @NotNull
    @Column(name = "consumer_id", nullable = false)
    private Long consumerId;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "comsumer_id")
    private Consumer comsumer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}