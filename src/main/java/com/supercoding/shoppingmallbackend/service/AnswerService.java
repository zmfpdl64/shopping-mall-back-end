package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.dto.request.answer.CreateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.response.answer.CreateAnswerResponse;
import com.supercoding.shoppingmallbackend.entity.Answer;
import com.supercoding.shoppingmallbackend.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }


    public CreateAnswerResponse createAnswer(CreateAnswerRequest request) {
        Answer answer = new Answer();

        answer.setQuestionIdx(request.getQuestionIdx());
        answer.setContent(request.getContent());
        answer.setIsDeleted(false);
        answer.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        Answer savedAnswer = answerRepository.save(answer);

        CreateAnswerResponse response = CreateAnswerResponse.builder()
                .id(savedAnswer.getId())
                .questionIdx(savedAnswer.getQuestionIdx())
                .sellerIdx(savedAnswer.getSellerIdx())
                .content(savedAnswer.getContent())
                .build();
        return response;
    }
}
