package com.supercoding.shoppingmallbackend.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("리뷰 요청 객체")
public class ReviewRequest {
    @ApiModelProperty(value = "상품 id", required = true)
    private Long productId;
    @ApiModelProperty(value = "리뷰 내용", required = true)
    private String content;
    @ApiModelProperty(value = "별점", required = true)
    private Double rating;
}
