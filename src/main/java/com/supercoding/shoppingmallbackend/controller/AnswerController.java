package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.answer.CreateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.response.answer.CreateAnswerResponse;
import com.supercoding.shoppingmallbackend.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions/answers")
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping
    public CommonResponse<Object> createAnswer(
            @RequestBody CreateAnswerRequest request){
        CreateAnswerResponse createAnswerResponse = answerService.createAnswer(request);
        return ApiUtils.success("답변 작성 완료",createAnswerResponse);
    }
}
