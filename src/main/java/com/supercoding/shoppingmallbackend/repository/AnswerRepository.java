package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Answer;
import com.supercoding.shoppingmallbackend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long>{

    @Query("delete FROM Answer a WHERE a.id =:answerId")
    @Modifying
    void hardDelete(@Param("answerId")Long answerId);

    List<Answer> findAllBySeller(Seller seller);

}
