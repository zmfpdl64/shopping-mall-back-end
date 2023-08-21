package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.dto.request.answer.CreateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.request.answer.UpdateAnswerRequest;
import com.supercoding.shoppingmallbackend.dto.response.answer.CreateAnswerResponse;
import com.supercoding.shoppingmallbackend.dto.response.answer.UpdateAnswerResponse;
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

    public UpdateAnswerResponse updateAnswer(Long id, UpdateAnswerRequest request) {
        Answer answer = answerRepository.findById(id).orElse(null);
        if(answer == null) {
            return null;
        }

        answer.setContent(request.getContent());

        Answer updatedAnswer = answerRepository.save(answer);

        return UpdateAnswerResponse.builder()
                .id(answer.getId())
                .questionIdx(answer.getQuestionIdx())
                .sellerIdx(answer.getSellerIdx())
                .content(updatedAnswer.getContent())
                .build();
    }

    public void deleteAnswer(Long id) {
        Answer answer = answerRepository.findById(id).orElse(null);
        if(answer != null){
            answerRepository.delete(answer);
        }
    }
}
