package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import com.supercoding.shoppingmallbackend.dto.request.answer.CreateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.request.answer.UpdateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.response.GetMyAnswerResponse;
import com.supercoding.shoppingmallbackend.security.AuthHolder;
import com.supercoding.shoppingmallbackend.service.AnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "문의 답변 API")
@RequestMapping("/api/v1/questions/answers")
public class AnswerController {

    private final AnswerService answerService;

    @ApiOperation(value = "문의 답변 작성")
    @PostMapping("/{questionIdx}")
    public CommonResponse<Object> createAnswer(
            @PathVariable("questionIdx") Long questionIdx,
            @RequestBody CreateAnswerRequest createAnswerRequest){
        Long userIdx = AuthHolder.getProfileIdx();
        answerService.createAnswer(createAnswerRequest,questionIdx,userIdx);
        return ApiUtils.success("작성 완료",null);
    }

    @ApiOperation(value = "답변 수정")
    @PutMapping("/{answer_idx}")
    public CommonResponse<Object> updateAnswer(
            @PathVariable("answer_idx") Long answerId,
            @RequestBody CreateAnswerRequest createAnswerRequest){
        Long profileIdx = AuthHolder.getProfileIdx();
        answerService.updateAnswerByAnswerId(answerId,profileIdx,createAnswerRequest);
        return ApiUtils.success(answerId+"번 답변 수정 성공",null);

    }

    @ApiOperation(value = "답변 삭제")
    @DeleteMapping("/{answer_idx}")
    public CommonResponse<Object> deleteQuestion(
            @PathVariable("answer_idx") Long answerId) {
        Long profileIdx = AuthHolder.getProfileIdx();
        answerService.deleteAnswerByAnswerId(answerId,profileIdx);
        return ApiUtils.success(answerId + "번 문의 삭제 성공", null);
    }

    @ApiOperation(value = "내가 작성한 답변 조회")
    @GetMapping("/me")
    public CommonResponse<Object> getAnswerWriterByMe() {
        Long profileIdx = AuthHolder.getProfileIdx();
        List<GetMyAnswerResponse> getMyAnswerResponse = answerService.getWriterByMe(profileIdx);
        return ApiUtils.success("내가 작성한 답변 조회 성공", getMyAnswerResponse);
    }

}