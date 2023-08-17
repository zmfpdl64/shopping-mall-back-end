package com.supercoding.shoppingmallbackend.dto.response;


import com.supercoding.shoppingmallbackend.common.util.DateUtils;
import com.supercoding.shoppingmallbackend.entity.Category;
import com.supercoding.shoppingmallbackend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailResponse {

    private Long productIdx;
    private String mainImageUrl;
    private String closingAt;
    private String title;
    private String companyName;
    private Long price;
    private double avgRating;
    private String playerCount;
    private String difficultyLevel;
    private String playTime;
    private String genre;
    private Long amount;
    private List<ProductImageResponse> imgUrls;

    public static ProductDetailResponse from(Product product, List<ProductImageResponse> imgUrlList, List<Category> categories) throws ParseException {
        return ProductDetailResponse.builder()
                .productIdx(product.getId())
                .mainImageUrl(product.getMainImageUrl())
                .closingAt(DateUtils.convertToString(product.getClosingAt()))
                .title(product.getTitle())
                .companyName(product.getSeller().getProfile().getName()) //실제로는 판매자 회원정보에서 가져올 부분
                .price(product.getPrice())
                .avgRating(3.5) //실제로는 리뷰에서 평균 내려야함
                .playerCount(categories.get(0).getName()) //실제로는 카테고리 테이블에서 가져와야 함
                .difficultyLevel(categories.get(2).getName())
                .playTime(categories.get(1).getName())
                .genre(product.getGenre().getName()) //실제로는 장르 테이블에서 코드값을 읽어 가져와야함
                .amount(product.getAmount())
                .imgUrls(imgUrlList)
                .build();
    }

}
