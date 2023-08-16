package com.supercoding.shoppingmallbackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateRequest extends ProductRequestBase {

    @Schema(description = "썸네일 첨부 필드")
    private MultipartFile mainImage;
    @Schema(description = "본문 이미지 첨부 필드")
    private List<MultipartFile> imageFiles;

}
