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
@Table(name = "consumer")
public class Consumer {
    @Id
    @Column(name = "idx", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "profile_idx", nullable = false)
    private Long profileIdx;

}