package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.entity.Product;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductSimpleResponse {
    private Long id;
    private String title;
    private String mainImageUrl;
    private String genre;
    private Long price;
    private Long leftQuantity;
    private Long sellerId;
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
