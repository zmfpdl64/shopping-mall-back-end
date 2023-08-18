package com.supercoding.shoppingmallbackend.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel(description = "쇼핑카트에 상품을 저장하기 위해 필요한 정보들을 담은 객체입니다.")
public class ShoppingCartItemRequest {
    @ApiModelProperty(required = true, value = "장바구니에 담을 상품의 id", dataType = "Long", example = "1")
    private Long productId;
    @ApiModelProperty(required = true, value = "장바구니에 담을 상품의 개수", dataType = "Long", example = "1")
    private Long amount;
}
