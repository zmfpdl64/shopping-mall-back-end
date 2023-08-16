package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Product;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KoeyProductResponse {
    private Long id;
    private String title;
    private String mainImageUrl;
    private String genre;
    private Long price;
    private Long amount;
    private Long sellerId;

    public static KoeyProductResponse from(Product entity) {
        return KoeyProductResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .mainImageUrl(entity.getMainImageUrl())
//                장르 객체 얻어오기
                .genre("퍼즐")
                .price(entity.getPrice())
                .amount(entity.getAmount())
                .sellerId(entity.getSellerIdx())
                .build();
    }
}
