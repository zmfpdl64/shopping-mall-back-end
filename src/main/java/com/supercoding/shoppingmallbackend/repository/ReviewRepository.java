package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.Review;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductAndIsDeletedIsFalse(Product product, Sort sort);
    Page<Review> findAllByProductAndIsDeletedIsFalse(Product product, Pageable pageable);
    List<Review> findAllByConsumerAndIsDeletedIsFalse(Consumer consumer);
    List<Review> findAllByConsumerAndIsDeletedIsFalseOrderByCreatedAtDesc(Consumer consumer);
    Page<Review> findAllByConsumerAndIsDeletedIsFalseOrderByCreatedAtDesc(Consumer consumer, Pageable pageable);
    Optional<Review> findByIdAndConsumerAndIsDeletedIsFalse(long reviewId, Consumer consumer);
}
