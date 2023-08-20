package com.supercoding.shoppingmallbackend.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("pagination page 결과를 담는 객체")
public class PaginationPageResponse<T> {
    @ApiModelProperty(value = "전체 페이지 개수", required = true)
    private Integer totalPages;
    @ApiModelProperty(value = "pagination으로 조회한 데이터들", required = true)
    private List<T> contents;
}
