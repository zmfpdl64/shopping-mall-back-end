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
    private Long id;
    private String title;
    private String mainImageUrl;
    private String genre;
    private Long price;
    private Long amount;
    private Long sellerId;
    private String closingAt;

    public static ProductSimpleResponse from(Product entity, Genre genre) {
        return ProductSimpleResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .mainImageUrl(entity.getMainImageUrl())
                .genre(genre.getName())
                .price(entity.getPrice())
                .amount(entity.getAmount())
                .sellerId(entity.getSellerIdx())
                .closingAt(DateUtils.convertToString(entity.getClosingAt()))
                .build();
    }
}
