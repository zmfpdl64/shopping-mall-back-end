package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findAllByConsumerAndIsDeletedIsFalseOrderByCreatedAtDesc(Consumer consumer);
    Page<ShoppingCart> findAllByConsumerAndIsDeletedIsFalseOrderByCreatedAtDesc(Consumer consumer, Pageable pageable);
    Optional<ShoppingCart> findByConsumerAndProductAndIsDeletedIsFalseOrderByCreatedAtDesc(Consumer consumer, Product product);
    void deleteAllByIsDeletedIsTrue();
}
