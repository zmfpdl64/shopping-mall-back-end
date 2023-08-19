package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.dto.request.ReviewRequest;
import com.supercoding.shoppingmallbackend.dto.response.ReviewResponse;
import com.supercoding.shoppingmallbackend.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
@Api(tags = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "리뷰 작성", notes = "리뷰를 작성합니다.")
    @PostMapping()
    public CommonResponse<ReviewResponse> createReview(
            @RequestBody @ApiParam(value = "리뷰에 관한 정보를 담은 객체", required = true) ReviewRequest request,
            @RequestPart(value = "리뷰 이미지 파일", required = false) @ApiParam(value = "리뷰 이미지 (선택)") MultipartFile imageFile) {
        return reviewService.createReview(request, imageFile);
    }
}
