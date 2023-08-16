package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.questions.CreateQuestionRequest;
import com.supercoding.shoppingmallbackend.dto.response.questions.CreateQuestionResponse;
import com.supercoding.shoppingmallbackend.service.QuestionService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public CommonResponse<Object> createQuestion(
            @RequestBody CreateQuestionRequest request) {
        CreateQuestionResponse createdQuestion = questionService.createQuestion(request);
        return ApiUtils.success("작성 완료",createdQuestion);
    }
}
