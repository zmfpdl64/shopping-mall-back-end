package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("select sc from ShoppingCart sc " +
            "join fetch sc.consumer c " +
            "join fetch sc.product p " +
            "join fetch c.profile pf " +
            "join fetch p.genre g " +
            "join fetch p.seller s " +
            "where c.id=:consumerId and sc.isDeleted=false " +
            "order by sc.createdAt desc")
    List<ShoppingCart> findAllByConsumerId(long consumerId);

    @Query("select sc from ShoppingCart sc " +
            "join fetch sc.consumer c " +
            "join fetch sc.product p " +
            "join fetch c.profile pf " +
            "join fetch p.genre g " +
            "join fetch p.seller s " +
            "where c.id=:consumerId and p.id=:productId and sc.isDeleted=false " +
            "limit 1")
    Optional<ShoppingCart> findByConsumerIdProductId(Long consumerId, Long productId);
}
