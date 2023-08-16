package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<ShoppingCart, Integer> {
    Optional<ShoppingCart> findByConsumerIdAndProductId(Long consumerId, Long productId);
}
