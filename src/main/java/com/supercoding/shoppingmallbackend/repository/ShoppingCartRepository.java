package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    
    Optional<ShoppingCart> findByConsumerIdAndProductId(Long consumerId, Long productId);

    List<ShoppingCart> findAllByConsumerId(long consumerId);
}
