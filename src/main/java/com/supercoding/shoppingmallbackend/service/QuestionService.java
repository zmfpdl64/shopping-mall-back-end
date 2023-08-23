package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.CommonErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ConsumerErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.ProductErrorCode;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.common.util.FilePath;
import com.supercoding.shoppingmallbackend.dto.request.questions.CreateQuestionRequest;
import com.supercoding.shoppingmallbackend.dto.request.questions.UpdateQuestionRequest;
import com.supercoding.shoppingmallbackend.dto.response.questions.CreateQuestionResponse;
import com.supercoding.shoppingmallbackend.dto.response.questions.GetQuestionResponse;
import com.supercoding.shoppingmallbackend.dto.response.questions.UpdateQuestionResponse;
import com.supercoding.shoppingmallbackend.entity.*;
import com.supercoding.shoppingmallbackend.repository.ConsumerRepository;
import com.supercoding.shoppingmallbackend.repository.ProductRepository;
import com.supercoding.shoppingmallbackend.repository.ProfileRepository;
import com.supercoding.shoppingmallbackend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ConsumerRepository consumerRepository;
    private final ProductRepository productRepository;
    private final AwsS3Service awsS3Service;
//    public GetQuestionResponse getQuestion(Long id) {
//        Question question = questionRepository.findById(id).orElse(null);
//        if (question == null) {
//            return null;
//        }
//
//        return GetQuestionResponse.builder()
//                .id(question.getId())
//                .productIdx(question.getProductIdx())
//                .consumerIdx(question.getConsumerIdx())
//                .title(question.getTitle())
//                .content(question.getContent())
//                .imageUrl(question.getImageUrl())
//                .build();
//    }



//    public UpdateQuestionResponse updateQuestion(Long id, UpdateQuestionRequest request) {
//        Question question = questionRepository.findById(id).orElse(null);
//        if (question == null) {
//            return null;
//        }
//
//        question.setTitle(request.getTitle());
//        question.setContent(request.getContent());
//        question.setImageUrl(request.getImageUrl());
//
//        Question updatedQuestion = questionRepository.save(question);
//
//        return UpdateQuestionResponse.builder()
//                .id(updatedQuestion.getId())
//                .productIdx(question.getProductIdx()) // 기존 값 유지
//                .consumerIdx(question.getConsumerIdx()) // 기존 값 유지
//                .title(updatedQuestion.getTitle())
//                .content(updatedQuestion.getContent())
//                .imageUrl(updatedQuestion.getImageUrl())
//                .build();
//    }



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

    public GetQuestionResponse getQuestionByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(ConsumerErrorCode.NOT_FOUND_BY_ID.getErrorCode()));
        GetQuestionResponse response = GetQuestionResponse.from(question);
        return response;
    }
}