package com.supercoding.shoppingmallbackend.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "주소 DTO")
public class AddressRequest {

    @ApiModelProperty(value = "수취인 이름", dataType = "String")
    private String name;
    @ApiModelProperty(value = "수취인 주소", dataType = "String")
    private String address;
    @ApiModelProperty(value = "수취인 상세 주소", dataType = "String")
    private String addressDetail;
    @ApiModelProperty(value = "수취인 휴대폰 번호", dataType = "String")
    private String phone;
    @ApiModelProperty(value = "수취인 우편 번호", dataType = "int")
    private Integer zipCode;


}
