package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.OrderByErrorCode;
import com.supercoding.shoppingmallbackend.dto.response.PaginationResponse;
import com.supercoding.shoppingmallbackend.dto.response.ReviewResponse;
import com.supercoding.shoppingmallbackend.entity.sortProperty.ReviewSortProperty;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import com.supercoding.shoppingmallbackend.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
@Api(tags = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "상품 리뷰 조회", notes = "상품의 모든 리뷰를 조회합니다.")
    @GetMapping("/{productId}")
    public CommonResponse<List<ReviewResponse>> getAllProductReview(
            @PathVariable @ApiParam(value = "상품 id", required = true) String productId,
            @RequestParam(value = "sortBy", required = false, defaultValue = "creation-date") @ApiParam("정렬 기준") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "DESC") @ApiParam("정렬 방향") String direction
    ) {
        String sortProperty = ReviewSortProperty.get(sortBy);
        if (sortProperty == null) throw OrderByErrorCode.INVALID_SORT_PARAMS.exception();
        Sort sort = createSort(sortProperty, direction);
        if (sort == null) throw OrderByErrorCode.INVALID_SORT_PARAMS.exception();

        try {
            long productIdLong = Long.parseLong(productId);
            return reviewService.getAllProductReview(productIdLong, sort);
        } catch (NumberFormatException e) {
            throw new CustomException(CommonErrorCode.INVALID_PATH_VARIABLE);
        }
    }

//    @ApiOperation(value = "상품 리뷰 조회 (pagination)", notes = "상품의 모든 리뷰를 조회합니다. 그런데 이제 이 pagination을 곁들인...")
//    @GetMapping("/{productId}/query")
//    public CommonResponse<PaginationResponse<ReviewResponse>> getAllProductReviewWithPagination(
//            @PathVariable @ApiParam(value = "상품 id", required = true) String productId,
//            @RequestParam("page") @ApiParam(value = "페이지 번호(0부터 시작)", required = true) String page,
//            @RequestParam("size") @ApiParam(value = "한 페이지에 보여줄 데이터 개수", required = true) String size){
//        try {
//            return reviewService.getAllProductREviewWithPagination(Long.parseLong(productId), Integer.parseInt(page), Integer.parseInt(size));
//        } catch (NumberFormatException e) {
//            throw new CustomException(CommonErrorCode.INVALID_QUERY_PARAM_OR_PATH_VARIABLE);
//        }
//    }

    @ApiOperation(value = "상품 리뷰 조회 (pagination)", notes = "상품의 모든 리뷰를 조회합니다. 그런데 이제 이 pagination을 곁들인...")
    @GetMapping("/{productId}/pagination")
    public CommonResponse<PaginationResponse<ReviewResponse>> getAllProductReviewWithPagination(
            @PathVariable @ApiParam(value = "상품 id", required = true) String productId,
            @RequestParam(value = "sortBy", required = false, defaultValue = "creation-date") @ApiParam("정렬 기준") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "DESC") @ApiParam("정렬 방향") String direction,
            @RequestParam("page") @ApiParam(value = "페이지 번호(0부터 시작)", required = true) String page,
            @RequestParam("size") @ApiParam(value = "한 페이지에 보여줄 데이터 개수", required = true) String size
    ){
        String sortProperty = ReviewSortProperty.get(sortBy);
        if (sortProperty == null) throw OrderByErrorCode.INVALID_SORT_PARAMS.exception();
        Sort sort = createSort(sortProperty, direction);
        if (sort == null) throw OrderByErrorCode.INVALID_SORT_PARAMS.exception();

        try {
            long productIdLong = Long.parseLong(productId);
            int pageInt = Integer.parseInt(page);
            int sizeInt = Integer.parseInt(size);

            if (pageInt < 0 || sizeInt < 1) throw new CustomException(CommonErrorCode.INVALID_QUERY_PARAM_OR_PATH_VARIABLE);

            PageRequest pageRequest = PageRequest.of(pageInt, sizeInt, sort);
            return reviewService.getAllProductREviewWithPagination(productIdLong, pageRequest);
        } catch (NumberFormatException e) {
            throw new CustomException(CommonErrorCode.INVALID_QUERY_PARAM_OR_PATH_VARIABLE);
        }
    }

    @ApiOperation(value = "내가 쓴 리뷰 조회", notes = "내가 작성한 모든 리뷰를 조회합니다.")
    @GetMapping()
    public CommonResponse<List<ReviewResponse>> getAllMyReview() {
        Long profileId = AuthHolder.getProfileIdx();
        return reviewService.getAllMyReview(profileId);
    }

    @ApiOperation(value = "내가 쓴 리뷰 조회 (pagination)", notes = "내가 작성한 모든 리뷰를 조회합니다. 그런데 이제 이 pagination을 곁들인...")
    @GetMapping("/query")
    public CommonResponse<PaginationResponse<ReviewResponse>> getAllMyReviewWithPagenation(
            @RequestParam("page") @ApiParam(value = "페이지 번호(0부터 시작)", required = true) String page,
            @RequestParam("size") @ApiParam(value = "한 페이지에 보여줄 데이터 개수", required = true) String size
    ) {
        Long profileId = AuthHolder.getProfileIdx();
        try {
            return reviewService.getAllMyReviewWithPagination(profileId, Integer.parseInt(page), Integer.parseInt(size));
        } catch (NumberFormatException e) {
            throw new CustomException(CommonErrorCode.INVALID_QUERY_PARAMETER);
        }
    }

    @ApiOperation(value = "리뷰 작성", notes = "리뷰를 작성합니다.")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<ReviewResponse> createReview(
            @RequestPart(required = false) @ApiParam(value = "리뷰 이미지 파일") MultipartFile imageFile,
            @RequestParam @ApiParam(value = "상품 id", required = true) Long productId,
            @RequestParam @ApiParam(value = "리뷰 내용", required = true) String content,
            @RequestParam @ApiParam(value = "별점", required = true) Double rating
    ) {
        Long profileId = AuthHolder.getProfileIdx();
        return reviewService.createReview(profileId, imageFile, productId, content, rating);
    }

    @ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정합니다.")
    @PutMapping(value = "/{reviewId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<ReviewResponse> modiryReview(
            @PathVariable @ApiParam(value = "리뷰 id", required = true) String reviewId,
            @RequestPart(required = false) @ApiParam(value = "리뷰 이미지 파일") MultipartFile imageFile,
            @RequestParam @ApiParam(value = "리뷰 내용", required = true) String content,
            @RequestParam @ApiParam(value = "별점", required = true) Double rating
    ){
        Long profileId = AuthHolder.getProfileIdx();
        long reviewIdLong = Long.parseLong(reviewId);
        try {
            return reviewService.modifyReview(profileId, reviewIdLong, imageFile, content, rating);
        } catch (NumberFormatException e) {
            throw new CustomException(CommonErrorCode.INVALID_PATH_VARIABLE);
        }
    }

    @ApiOperation(value = "리뷰 삭제", notes = "리뷰를 삭제합니다.")
    @DeleteMapping("/query")
    public CommonResponse<List<ReviewResponse>> deleteReviews(@RequestParam("id") @ApiParam(value = "삭제할 리뷰 id", required = true) Set<String> ids) {
        Long profileId = AuthHolder.getProfileIdx();
        Set<Long> idSet = ids.stream().map(Long::parseLong).collect(Collectors.toSet());
        return reviewService.softDeleteReviews(profileId, idSet);
    }

    private Sort createSort(String sortProperty, String direction) {
        return direction.equals("DESC") ? Sort.by(Sort.Order.desc(sortProperty))
                : direction.equals("ASC") ? Sort.by(Sort.Order.asc(sortProperty))
                : null;
    }
}
