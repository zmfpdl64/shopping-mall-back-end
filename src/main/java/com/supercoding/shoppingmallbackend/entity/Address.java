package com.supercoding.shoppingmallbackend.entity;

import com.supercoding.shoppingmallbackend.dto.request.AddressRequest;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
@SQLDelete(sql = "UPDATE addresses as a SET a.is_deleted = true WHERE idx = ?")
@Where(clause = "is_deleted = false")
public class Address extends CommonField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Size(max = 63)
    @Column(name = "name",length = 63)
    private String name;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Size(max = 127)
    @Column(name = "address", length = 127)
    private String address;

    @Size(max = 127)
    @Column(name = "address_detail", length = 127)
    private String addressDetail;

    @Size(max = 63)
    @Column(name = "phone", length = 63)
    private String phone;


    public static Address from(AddressRequest addressRequest, Profile profile) {
        return Address.builder()
                .profile(profile)
                .address(addressRequest.getAddress())
                .addressDetail(addressRequest.getAddressDetail())
                .name(addressRequest.getName())
                .phone(addressRequest.getPhone())
                .zipCode(addressRequest.getZipCode())
                .build();
    }

}