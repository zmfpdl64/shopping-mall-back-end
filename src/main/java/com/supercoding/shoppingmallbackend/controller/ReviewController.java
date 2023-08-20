package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.dto.request.ReviewRequest;
import com.supercoding.shoppingmallbackend.dto.response.PaginationPageResponse;
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
import java.util.List;

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
            @RequestParam @ApiParam(value = "상품 id", required = true) Long productId,
            @RequestParam @ApiParam(value = "리뷰 내용", required = true) String content,
            @RequestParam @ApiParam(value = "별점", required = true) Double rating
    ) {
        return reviewService.createReview(imageFile, productId, content, rating);
    }

    @ApiOperation(value = "상품 리뷰 조회", notes = "상품의 모든 리뷰를 조회합니다.")
    @GetMapping("/{productId}")
    public CommonResponse<List<ReviewResponse>> getAllProductReview(@PathVariable String productId) {
        try {
            return reviewService.getAllProductReview(Long.parseLong(productId));
        } catch (NumberFormatException e) {
            throw new CustomException(CommonErrorCode.INVALID_PATH_VARIABLE);
        }
    }

    @ApiOperation(value = "상품 리뷰 조회 (pagination)", notes = "상품의 모든 리뷰를 조회합니다. 그런데 이제 이 pagination을 곁들인...")
    @GetMapping("/{productId}/query")
    public CommonResponse<PaginationPageResponse<ReviewResponse>> getAllProductReviewWithPagination(
            @PathVariable String productId,
            @RequestParam("page") String page,
            @RequestParam("size") String size){
        try {
            return reviewService.getAllProductREviewWithPagination(Long.parseLong(productId), Integer.parseInt(page), Integer.parseInt(size));
        } catch (NumberFormatException e) {
            throw new CustomException(CommonErrorCode.INVALID_QUERY_PARAM_OR_PATH_VARIABLE);
        }
    }

    @ApiOperation(value = "내가 쓴 리뷰 조회", notes = "내가 작성한 모든 리뷰를 조회합니다.")
    @GetMapping()
    public CommonResponse<List<ReviewResponse>> getAllMyReview() {
        return reviewService.getAllMyReview();
    }

}
