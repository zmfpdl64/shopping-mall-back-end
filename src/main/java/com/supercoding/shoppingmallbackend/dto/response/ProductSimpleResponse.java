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
@ApiModel("상품 간단 정보")
public class ProductSimpleResponse {
    @ApiModelProperty(required = true, value = "상품 id", example = "1")
    private Long id;
    @ApiModelProperty(required = true, value = "상품 이름", example = "쉐입 퍼즐: 콜로세움 600 PCS")
    private String title;
    @ApiModelProperty(required = true, value = "상품 썸네일 이미지 URL", example = "https://boardm.co.kr/upload/product/img2/img_largeupfilenm_1688696198_a.jpg")
    private String mainImageUrl;
    @ApiModelProperty(required = true, value = "상품 장르", example = "퍼즐")
    private String genre;
    @ApiModelProperty(required = true, value = "상품 가격", example = "20000")
    private Long price;
    @ApiModelProperty(required = true, value = "상품 재고", example = "9998")
    private Long leftQuantity;
    @ApiModelProperty(required = true, value = "판매자 id", example = "1")
    private Long sellerId;
    @ApiModelProperty(required = true, value = "판매 마감 일자", example = "24.08.18")
    private String closingAt;

    public static ProductSimpleResponse from(Product product) {
        return ProductSimpleResponse.builder()
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
