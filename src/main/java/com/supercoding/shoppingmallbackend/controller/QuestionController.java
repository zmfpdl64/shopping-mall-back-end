package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.questions.CreateQuestionRequest;

import com.supercoding.shoppingmallbackend.dto.request.questions.UpdateQuestionRequest;
import com.supercoding.shoppingmallbackend.dto.response.questions.GetQuestionResponse;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import com.supercoding.shoppingmallbackend.service.QuestionService;


import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @ApiOperation(value = "문의 조회")
    @GetMapping("/{question_idx}")
    public CommonResponse<Object> getQuestion(
            @PathVariable("question_idx") Long questionId) {
        GetQuestionResponse question = questionService.getQuestionByQuestionId(questionId);
        return ApiUtils.success("조회 완료", question);
    }
    @ApiOperation(value = "판매자 또는 구매자의 문의 목록 조회")
    @GetMapping
    public CommonResponse<Object> getQuestions() {
        Long userIdx = AuthHolder.getProfileIdx();

//        List<GetQuestionResponse> questions;
//
//        if ("seller".equals(userType)) {
//            questions = questionService.getQuestionsBySellerId(userId);
//        } else if ("consumer".equals(userType)) {
//            questions = questionService.getQuestionsByConsumerId(userId);
//        } else {
//            return ApiUtils.fail(400, "유효하지 않은 사용자 유형입니다.");
//        }
//
//        return ApiUtils.success("문의 목록 조회 완료", questions);
        return null;
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
    @PutMapping("/{id}")
    public CommonResponse<Object> updateQuestion(
            @PathVariable Long id,
            @RequestBody UpdateQuestionRequest request) {
//        UpdateQuestionResponse updatedQuestion = questionService.updateQuestion(id, request);
        return ApiUtils.success("수정 완료", null);
    }

//    @ApiOperation(value = "문의 삭제")
//    @DeleteMapping
//    public CommonResponse<Object> deleteQuestion() {
//        Long userIdx = AuthHolder.getProfileIdx();
//        questionService.deleteQuestion(userIdx);
//        return CommonResponse.success("문의 삭제 성공", null);
//    }
}
