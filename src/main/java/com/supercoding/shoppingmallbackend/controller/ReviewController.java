package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.dto.request.ReviewRequest;
import com.supercoding.shoppingmallbackend.dto.response.ReviewResponse;
import com.supercoding.shoppingmallbackend.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
@Api(tags = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "리뷰 작성", notes = "리뷰를 작성합니다.")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<ReviewResponse> createReview(
            @RequestPart(required = false) @ApiParam(value = "리뷰 이미지 파일") MultipartFile imageFile,
            @RequestParam() @ApiParam(value = "상품 id", required = true) Long productId,
            @RequestParam @ApiParam(value = "리뷰 내용", required = true) String content,
            @RequestParam @ApiParam(value = "별점", required = true) Double rating
    ) {
        return reviewService.createReview(imageFile, productId, content, rating);
    }
}
