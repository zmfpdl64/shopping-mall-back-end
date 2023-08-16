package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.dto.request.questions.CreateQuestionRequest;
import com.supercoding.shoppingmallbackend.dto.request.questions.UpdateQuestionRequest;
import com.supercoding.shoppingmallbackend.dto.response.questions.CreateQuestionResponse;
import com.supercoding.shoppingmallbackend.dto.response.questions.GetQuestionResponse;
import com.supercoding.shoppingmallbackend.dto.response.questions.UpdateQuestionResponse;
import com.supercoding.shoppingmallbackend.entity.Question;
import com.supercoding.shoppingmallbackend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public GetQuestionResponse getQuestion(Long id) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question == null) {
            return null;
        }

        return GetQuestionResponse.builder()
                .id(question.getId())
                .productIdx(question.getProductIdx())
                .consumerIdx(question.getConsumerIdx())
                .title(question.getTitle())
                .content(question.getContent())
                .imageUrl(question.getImageUrl())
                .build();
    }


    public CreateQuestionResponse createQuestion(CreateQuestionRequest request) {
        Question question = new Question();

        question.setProductIdx(request.getProductIdx());
        question.setConsumerIdx(request.getConsumerIdx());
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setImageUrl(request.getImageUrl());
        question.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        question.setIsDeleted(false);

        Question savedQuestion = questionRepository.save(question);

        // CreateQuestionResponse 객체 생성 및 필드 설정
        CreateQuestionResponse createdQuestion = CreateQuestionResponse.builder()
                .id(savedQuestion.getId())
                .productIdx(savedQuestion.getProductIdx())
                .consumerIdx(savedQuestion.getConsumerIdx())
                .title(savedQuestion.getTitle())
                .content(savedQuestion.getContent())
                .imageUrl(savedQuestion.getImageUrl())
                .build();

        return createdQuestion;
    }
    public UpdateQuestionResponse updateQuestion(Long id, UpdateQuestionRequest request) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question == null) {
            return null;
        }
        
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setImageUrl(request.getImageUrl());

        Question updatedQuestion = questionRepository.save(question);

        return UpdateQuestionResponse.builder()
                .id(updatedQuestion.getId())
                .productIdx(question.getProductIdx()) // 기존 값 유지
                .consumerIdx(question.getConsumerIdx()) // 기존 값 유지
                .title(updatedQuestion.getTitle())
                .content(updatedQuestion.getContent())
                .imageUrl(updatedQuestion.getImageUrl())
                .build();
    }
}