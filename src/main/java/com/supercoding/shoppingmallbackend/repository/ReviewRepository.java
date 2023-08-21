package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.Review;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductAndIsDeletedIsFalse(Product product, Sort sort);

    @Query(value = "select r from Review r " +
            "join fetch r.product p " +
            "join fetch r.consumer c " +
            "join fetch p.genre g " +
            "join fetch p.seller s " +
            "join fetch c.profile pf  " +
            "where p.id=:productId and r.isDeleted=false " +
            "order by r.createdAt desc",
            countQuery = "select count(r) from Review r " +
                    "where r.product.id=:productId and r.isDeleted=false")
    Page<Review> findAllByProductIdWithPagination(long productId, Pageable pageable);

    @Query("select r from Review r " +
            "join fetch r.product p " +
            "join fetch r.consumer c " +
            "join fetch p.genre g " +
            "join fetch c.profile pf  " +
            "where c.id=:#{#consumer.getId()} and r.isDeleted=false " +
            "order by r.createdAt desc")
    List<Review> findAllByConsumer(Consumer consumer);

    @Query(value = "select r from Review r " +
            "join fetch r.product p " +
            "join fetch r.consumer c " +
            "join fetch p.genre g " +
            "join fetch c.profile pf  " +
            "where c.id=:#{#consumer.getId()} and r.isDeleted=false " +
            "order by r.createdAt desc",
            countQuery = "select count(r) from Review r " +
                    "where r.consumer.id=:#{#consumer.getId()} and r.isDeleted=false")
    Page<Review> findPageByConsumer(Consumer consumer, Pageable pageable);

    @Query(value = "select r from Review r " +
            "join fetch r.product p " +
            "join fetch r.consumer c " +
            "join fetch p.genre g " +
            "join fetch c.profile pf  " +
            "where c.id=:#{#consumer.getId()} and r.isDeleted=false and r.id=:reviewId ")
    Optional<Review> findByConsumerAndReviewId(Consumer consumer, long reviewId);
}
