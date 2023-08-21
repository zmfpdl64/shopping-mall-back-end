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
    public static ProductSimpleResponse from(Product product) {
        return ProductSimpleResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .mainImageUrl(product.getMainImageUrl())
                .build();
    }
}
