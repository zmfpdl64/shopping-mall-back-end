package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.ProductContentImage;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageResponse {

    private Long imgIdx;
    private String imgUrl;

    public static ProductImageResponse from(ProductContentImage productContentImage) {
        return ProductImageResponse.builder()
                .imgIdx(productContentImage.getId())
                .imgUrl(productContentImage.getImageUrl())
                .build();
    }

}
