package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.entity.Genre;
import com.supercoding.shoppingmallbackend.entity.Product;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductSimpleResponse {
    private Long productId;
    private String title;
    private String mainImageUrl;
    private String genre;
    private Long price;
    private Long leftQuantity;
    private Long sellerId;
    private String closingAt;

    public static ProductSimpleResponse from(Product product) {
        return ProductSimpleResponse.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .mainImageUrl(product.getMainImageUrl())
//                .genre(product.getGenre())
                .genre("장르") // 나중에 수정
                .price(product.getPrice())
                .leftQuantity(product.getAmount())
                .sellerId(product.getSellerIdx())
                .closingAt(DateUtils.convertToString(product.getClosingAt()))
                .build();
    }
}
