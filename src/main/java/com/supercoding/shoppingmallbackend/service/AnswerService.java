package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.AnswerErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.dto.request.answer.CreateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.request.answer.UpdateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.response.GetMyAnswerResponse;
import com.supercoding.shoppingmallbackend.entity.Answer;
import com.supercoding.shoppingmallbackend.entity.Question;
import com.supercoding.shoppingmallbackend.entity.Seller;
import com.supercoding.shoppingmallbackend.repository.AnswerRepository;
import com.supercoding.shoppingmallbackend.repository.QuestionRepository;
import com.supercoding.shoppingmallbackend.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final SellerRepository sellerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    // 답변 작성
    @Transactional
    public void createAnswer(CreateAnswerRequest createAnswerRequest, Long profileIdx) {
        Long validProfileIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
        Seller seller = sellerRepository.findByProfile_Id(validProfileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
        Question question = questionRepository.findById(createAnswerRequest.getQuestionIdx())
                .orElseThrow(() -> new CustomException(CommonErrorCode.INVALID_INPUT_VALUE.getErrorCode()));
        Optional.ofNullable(question.getAnswer()).ifPresent(answer -> {
            throw new CustomException(AnswerErrorCode.ALREADY_HAVE_ANSWER);
        });
        Answer answer = Answer.from(createAnswerRequest,seller,question);
        answerRepository.save(answer);

    }

    @Transactional
    public void deleteAnswerByAnswerId(Long answerId, Long profileIdx) {
        Answer variAnswer = validProfileAndAnswer(answerId,profileIdx);
        answerRepository.hardDelete(variAnswer.getId());
    }
    private Answer validProfileAndAnswer(Long answerId, Long profileIdx) {
        Long validQuestionIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.INVALID_INPUT_VALUE.getErrorCode()));
        if(!Objects.equals(answer.getSeller().getProfile().getId(), validQuestionIdx)){
            throw new CustomException(UserErrorCode.NOT_AUTHORIZED.getErrorCode());
        }
        return answer;
    }

    public void updateAnswerByAnswerId(Long answerId, Long profileIdx, UpdateAnswerRequest updateAnswerRequest) {
        Answer originAnswer = validProfileAndAnswer(answerId,profileIdx);
        Answer updateAnswer = Answer.from(originAnswer,updateAnswerRequest);

        answerRepository.save(updateAnswer);
    }

    public List<GetMyAnswerResponse> getWriterByMe(Long profileIdx) {
        Long validProfileIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));

        Seller seller = sellerRepository.findByProfile_Id(validProfileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));

        List<Answer> answerList = answerRepository.findAllBySeller(seller);

        return answerList.stream().map(GetMyAnswerResponse::from).collect(Collectors.toList());
    }
}