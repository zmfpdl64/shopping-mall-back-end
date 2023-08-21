package com.supercoding.shoppingmallbackend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "consumer")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Consumer extends CommonField{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "profile_idx")
    private Profile profile;

}