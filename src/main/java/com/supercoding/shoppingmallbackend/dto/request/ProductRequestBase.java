package com.supercoding.shoppingmallbackend.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "판매 등록 DTO")
public class ProductRequestBase {

    @NotBlank(message = "상품 타이틀을 입력해주세요.")
    @Schema(description = "게임 타이틀 입력 필드", defaultValue = "할리갈리", example = "할리갈리")
    @ApiModelProperty(value = "게임 타이틀 입력 필드", dataType = "String")
    private String title;
    @NotBlank(message = "가격을 입력해주세요.")
    @Schema(description = "가격 입력 필드")
    @ApiModelProperty(value = "가격 입력 필드", dataType = "Long")
    private Long price;
    @NotBlank(message = "장르 코드를 입력해주세요.")
    @Schema(description = "장르 코드 입력 필드")
    @ApiModelProperty(value = "장르 코드 입력 필드", dataType = "Long")
    private Long genre;
    @NotBlank(message = "플레이 타임을 선택해주세요.")
    @Schema(description = "플레이 타임 입력 필드")
    @ApiModelProperty(value = "플레이 타임 입력 필드 (\"30분 미만\", \"30분~90분\", \"60분~90분\", \"90분 이상\")", dataType = "String")
    private String playTime;
    @NotBlank(message = "참가 인원수를 선택해주세요.")
    @Schema(description = "참가 인원수 입력 필드")
    @ApiModelProperty(value = "참가 인원수 입력 필드 (\"2인 전용\", \"2~4인 전용\", \"5인 이상\", \"협력/팀플레이\", \"1인 가능\")", dataType = "String")
    private String playerCount;
    @NotBlank(message = "난이도를 선택해주세요.")
    @Schema(description = "난이도 입력 필드")
    @ApiModelProperty(value = "난이도 입력 필드 (\"초급\", \"중급\", \"상급\", \"최상급\")", dataType = "String")
    private String difficultyLevel;
    @NotBlank(message = "총 재고 수를 입력해주세요.")
    @Schema(description = "재고 수 입력 필드")
    @ApiModelProperty(value = "총 재고 수 입력 필드", dataType = "String")
    private Long amount;

    @NotBlank(message = "마감 기한을 입력해주세요.")
    @Schema(description = "마감 기한 입력 필드")
    @ApiModelProperty(value = "마감 기한 입력 필드 (\"2023.09.18\")", dataType = "String")
    private String closingAt;
}
