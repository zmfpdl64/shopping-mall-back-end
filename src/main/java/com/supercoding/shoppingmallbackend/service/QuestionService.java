package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProductErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.common.util.FilePath;
import com.supercoding.shoppingmallbackend.dto.request.questions.CreateQuestionRequest;

import com.supercoding.shoppingmallbackend.dto.request.questions.UpdateQuestionRequest;
import com.supercoding.shoppingmallbackend.dto.response.questions.GetQuestionResponse;
import com.supercoding.shoppingmallbackend.entity.*;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.ProductRepository;
import com.supercoding.shoppingmallbackend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ConsumerRepository consumerRepository;
    private final ProductRepository productRepository;
    private final AwsS3Service awsS3Service;

    // 문의 작성
    @Transactional
    public void createQuestion(CreateQuestionRequest request, MultipartFile imageFile, Long profileIdx) {
        Long validProfileIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
        Consumer consumer = consumerRepository.findByProfileIdAndIsDeletedIsFalse(validProfileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
        Product product = productRepository.findById(request.getProductIdx())
                .orElseThrow(() -> new CustomException(ProductErrorCode.NOTFOUND_PRODUCT));
        Question question = Question.from(request, consumer, product);
        questionRepository.save(question);
        if (question.getId() != null) {
            uploadQuestionImage(imageFile,question);
        }

    }
    private void uploadQuestionImage(MultipartFile thumbNailFile, Question question) {
        try {
            if (thumbNailFile != null) {
                String url = awsS3Service.upload(thumbNailFile,
                        FilePath.QUESTION_IMAGE_DIR.getPath() + question.getId());
                question.setImageUrl(url);
            }
        } catch (IOException e) {
            throw new CustomException(CommonErrorCode.FAIL_TO_SAVE.getErrorCode());
        }
    }

    // 문의 조회
    @Transactional
    public GetQuestionResponse getQuestionByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.INVALID_INPUT_VALUE.getErrorCode()));
        GetQuestionResponse response = GetQuestionResponse.from(question);
        return response;
    }
    // 문의 수정
    @Transactional
    public void updateQuestionByQuestionId(Long questionId, Long profileIdx, UpdateQuestionRequest updateQuestionRequest, MultipartFile imageFile) {
        Question originQuestion = validProfileAndQuestion(questionId,profileIdx);
        Question updateQuestion = Question.from(originQuestion,updateQuestionRequest);
        questionRepository.save(updateQuestion);
    }

    private Question validProfileAndQuestion(Long questionId, Long profileIdx) {
        Long validQuestionIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));
        Question question =questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.INVALID_INPUT_VALUE.getErrorCode()));
        if(!Objects.equals(question.getConsumer().getProfile().getId(), validQuestionIdx)){
            throw new CustomException(UserErrorCode.NOT_AUTHORIZED.getErrorCode());
        }
        return question;
    }

    // 문의 삭제
    @Transactional
    public void deleteQuestionByQuestionId(Long questionId, Long profileIdx) {
        Question valiQuestion = validProfileAndQuestion(questionId,profileIdx);
        questionRepository.deleteById(valiQuestion.getId());
    }
}