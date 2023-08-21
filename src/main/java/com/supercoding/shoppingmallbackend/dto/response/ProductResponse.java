package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("상품 일반 정보")
public class ProductResponse {
    @ApiModelProperty(required = true, value = "상품 id")
    private Long id;
    @ApiModelProperty(required = true, value = "상품 이름")
    private String title;
    @ApiModelProperty(required = true, value = "상품 썸네일 이미지 URL")
    private String mainImageUrl;
    @ApiModelProperty(required = true, value = "상품 장르")
    private String genre;
    @ApiModelProperty(required = true, value = "상품 가격")
    private Long price;
    @ApiModelProperty(required = true, value = "상품 재고")
    private Long leftQuantity;
    @ApiModelProperty(required = true, value = "상품 판매자 id")
    private Long sellerId;
    @ApiModelProperty(required = true, value = "상품 마감일자")
    private String closingAt;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .mainImageUrl(product.getMainImageUrl())
                .genre(product.getGenre().getName())
                .price(product.getPrice())
                .leftQuantity(product.getAmount())
                .sellerId(product.getSeller().getId())
                .closingAt(DateUtils.convertToString(product.getClosingAt()))
                .build();
    }
}
