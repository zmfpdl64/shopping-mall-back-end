package com.supercoding.shoppingmallbackend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "seller")
public class Seller extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "profile_idx", nullable = false)
    private Profile profile;

}