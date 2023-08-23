package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.questions.CreateQuestionRequest;

import com.supercoding.shoppingmallbackend.dto.request.questions.UpdateQuestionRequest;
import com.supercoding.shoppingmallbackend.dto.response.questions.GetQuestionResponse;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import com.supercoding.shoppingmallbackend.service.QuestionService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequiredArgsConstructor
@RestController
@Api(tags = "문의 API")
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @ApiOperation(value = "문의 조회")
    @GetMapping("/{product_idx}")
    public CommonResponse<Object> getQuestion(
            @PathVariable("product_idx") Long productIdx) {
        List<GetQuestionResponse> question = questionService.getQuestionByQuestionId(productIdx);
        return ApiUtils.success("조회 완료", question);
    }

    @ApiOperation(value = "문의 작성")
    @PostMapping(value = "/", consumes = "multipart/form-data")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data",
            schema = @Schema(implementation = MultipartFile.class)))

    public CommonResponse<Object> createQuestion(
            @ModelAttribute CreateQuestionRequest request,
            @RequestPart(value = "imageFile", required = false)  MultipartFile imageFile) {
        Long userIdx = AuthHolder.getProfileIdx();
        questionService.createQuestion(request,imageFile,userIdx);
        return ApiUtils.success("작성 완료",null);
    }

    @ApiOperation(value = "문의 수정")
    @PostMapping(value = "/{question_idx}", consumes = "multipart/form-data")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "multipart/form-data",
            schema = @Schema(implementation = MultipartFile.class)))

    public CommonResponse<Object> updateQuestion(
            @PathVariable("question_idx") Long questionId,
            @ModelAttribute UpdateQuestionRequest updateQuestionRequest,
            @RequestPart(value = "imageFile", required = false)  MultipartFile imageFile) {
        Long profileIdx = AuthHolder.getProfileIdx();
        questionService.updateQuestionByQuestionId(questionId,profileIdx,updateQuestionRequest,imageFile);
        return ApiUtils.success(questionId + "번 문의 수정 성공", null);
    }

    @ApiOperation(value = "문의 삭제")
    @DeleteMapping("/{question_idx}")
    public CommonResponse<Object> deleteQuestion(
            @PathVariable("question_idx") Long questionId) {
        Long profileIdx = AuthHolder.getProfileIdx();
        questionService.deleteQuestionByQuestionId(questionId,profileIdx);
        return ApiUtils.success(questionId + "번 문의 삭제 성공", null);
    }
}
