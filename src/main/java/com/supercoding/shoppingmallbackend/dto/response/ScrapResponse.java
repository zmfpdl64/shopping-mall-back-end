package com.supercoding.shoppingmallbackend.dto.response;

import com.supercoding.shoppingmallbackend.entity.Scrap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("찜한 정보를 담고 있는 객체")
public class ScrapResponse {
    @ApiModelProperty(value = "찜 id", required = true)
    private Long id;
    @ApiModelProperty(value = "찜한 상품 정보", required = true)
    private ProductSimpleResponse product;
    @ApiModelProperty(value = "찜한 구매자 정보", required = true)
    private ConsumerResponse consumer;

    public static ScrapResponse from(Scrap scrap) {
        return ScrapResponse.builder()
                .id(scrap.getId())
                .product(ProductSimpleResponse.from(scrap.getProduct()))
                .consumer(ConsumerResponse.from(scrap.getConsumer()))
                .build();
    }
}
