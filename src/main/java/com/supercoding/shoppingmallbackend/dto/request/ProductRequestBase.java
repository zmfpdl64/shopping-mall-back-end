package com.supercoding.shoppingmallbackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "판매 등록 DTO")
public class ProductRequestBase {

    @NotBlank(message = "상품 타이틀을 입력해주세요.")
    @Schema(description = "게임 타이틀 입력 필드", defaultValue = "할리갈리", example = "할리갈리")
    private String title;
    @NotBlank(message = "가격을 입력해주세요.")
    @Schema(description = "가격 입력 필드")
    private Long price;
    @NotBlank(message = "장르 코드를 입력해주세요.")
    @Schema(description = "장르 코드 입력 필드")
    private Long genre;
    @NotBlank(message = "플레이 타임을 선택해주세요.")
    @Schema(description = "플레이 타임 입력 필드")
    private String playTime;
    @NotBlank(message = "참가 인원수를 선택해주세요.")
    @Schema(description = "참가 인원수 입력 필드")
    private String playerCount;
    @NotBlank(message = "난이도를 선택해주세요.")
    @Schema(description = "난이도 입력 필드")
    private String difficultyLevel;
    @NotBlank(message = "총 재고 수를 입력해주세요.")
    @Schema(description = "재고 수 입력 필드")
    private Long amount;

}
