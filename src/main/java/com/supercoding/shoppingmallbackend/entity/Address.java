package com.supercoding.shoppingmallbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Size(max = 63)
    @NotNull
    @Column(name = "name", nullable = false, length = 63)
    private String name;

    @Size(max = 127)
    @Column(name = "address", length = 127)
    private String address;

    @Size(max = 127)
    @Column(name = "address_detail", length = 127)
    private String addressDetail;

    @Size(max = 63)
    @Column(name = "phone", length = 63)
    private String phone;

}