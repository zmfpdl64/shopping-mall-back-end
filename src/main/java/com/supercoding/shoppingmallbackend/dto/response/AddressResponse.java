package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

    private String name;

    private String address;

    private String addressDetail;

    private String phone;

    private Integer zipCode;

    public static AddressResponse from(Address address) {
        return AddressResponse.builder()
                .name(address.getName())
                .address(address.getAddress())
                .addressDetail(address.getAddressDetail())
                .phone(address.getPhone())
                .zipCode(address.getZipCode())
                .build();
    }

}
