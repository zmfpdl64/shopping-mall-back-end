package com.supercoding.shoppingmallbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "scrap_list")
public class ScrapList {
    @Id
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "prdouct_idx", nullable = false)
    private Long prdouctIdx;

    @NotNull
    @Column(name = "consumer_idx", nullable = false)
    private Long consumerIdx;

}