package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r " +
            "join fetch r.product p " +
            "join fetch r.consumer " +
            "where p.id=:productId and r.isDeleted=false")
    List<Review> findAllByProductId(long productId);
}
