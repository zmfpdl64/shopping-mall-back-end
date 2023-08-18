package com.supercoding.shoppingmallbackend.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("쇼핑카트 아이템 객체")
public class ShoppingCartItemRequest {
    @ApiModelProperty(required = true, value = "장바구니에 담을 상품의 id", example = "1")
    private Long productId;
    @ApiModelProperty(required = true, value = "장바구니에 담을 상품의 개수", example = "1")
    private Long amount;
}
