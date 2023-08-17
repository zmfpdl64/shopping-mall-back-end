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
    private Long quantityLeft;
    private Long sellerId;
    private String closingAt;
}
