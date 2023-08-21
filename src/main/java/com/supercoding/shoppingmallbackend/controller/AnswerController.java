package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.answer.CreateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.request.answer.UpdateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.response.answer.CreateAnswerResponse;
import com.supercoding.shoppingmallbackend.dto.response.answer.UpdateAnswerResponse;
import com.supercoding.shoppingmallbackend.service.AnswerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions/answers")
public class AnswerController {
    private final AnswerService answerService;

    @ApiOperation(value = "문의 답변을 작성.")
    @PostMapping
    public CommonResponse<Object> createAnswer(
            @RequestBody CreateAnswerRequest request){
        CreateAnswerResponse createAnswerResponse = answerService.createAnswer(request);
        return ApiUtils.success("답변 작성 완료",createAnswerResponse);
    }
    @ApiOperation(value = "문의 답변을 수정.")
    @PutMapping("/{id}")
    public CommonResponse<Object> updateAnswer(
            @PathVariable Long id,
            @RequestBody UpdateAnswerRequest request) {
        UpdateAnswerResponse updateAnswer = answerService.updateAnswer(id,request);
        if( updateAnswer == null ){
            return ApiUtils.fail(404,"존재하지 않습니다.");
        }
        return ApiUtils.success("수정완료",updateAnswer);
    }
    @ApiOperation(value = "문의 답변을 삭제.")
    @DeleteMapping("/{id}")
    public CommonResponse<Object> deleteAnswer(@PathVariable Long id){
        answerService.deleteAnswer(id);
        return ApiUtils.success("삭제 완료",null);
    }
}
