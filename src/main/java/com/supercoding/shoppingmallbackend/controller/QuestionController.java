package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.questions.CreateQuestionRequest;

import com.supercoding.shoppingmallbackend.dto.response.questions.CreateQuestionResponse;
import com.supercoding.shoppingmallbackend.dto.response.questions.GetQuestionResponse;
import com.supercoding.shoppingmallbackend.service.QuestionService;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    // 문의 조회
    @GetMapping("/{id}")
    public CommonResponse<Object> getQuestion(
            @PathVariable Long id) {
        GetQuestionResponse question = questionService.getQuestion(id);
        if( question == null){
            return ApiUtils.fail(404,"존재하지 않습니다.");
        }
        return ApiUtils.success("조회 완료", question);
    }

    // 문의 작성
    @PostMapping
    public CommonResponse<Object> createQuestion(
            @RequestBody CreateQuestionRequest request) {
        CreateQuestionResponse createdQuestion = questionService.createQuestion(request);
        return ApiUtils.success("작성 완료",createdQuestion);
    }
    
}
