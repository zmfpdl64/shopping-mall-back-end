package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r " +
            "join fetch r.product p " +
            "join fetch r.consumer c " +
            "join fetch p.genre g " +
            "join fetch c.profile pf  " +
            "where p.id=:productId and r.isDeleted=false")
    List<Review> findAllByProductId(long productId);

    @Query("select r from Review r " +
            "join fetch r.product p " +
            "join fetch r.consumer c " +
            "join fetch p.genre g " +
            "join fetch c.profile pf  " +
            "where c.id=#{consumer.id} and r.isDeleted=false")
    List<Review> findAllByConsumer(Consumer consumer);
}
