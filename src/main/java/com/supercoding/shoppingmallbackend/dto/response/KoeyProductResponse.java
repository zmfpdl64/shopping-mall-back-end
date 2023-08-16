package com.supercoding.shoppingmallbackend.dto.response;

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
}
