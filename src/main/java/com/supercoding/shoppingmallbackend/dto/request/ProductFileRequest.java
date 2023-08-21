package com.supercoding.shoppingmallbackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 수정 시 본문 사진 요청 DTO")
public class ProductFileRequest extends ProductRequestBase {

    private List<Long> imgIds;
}
