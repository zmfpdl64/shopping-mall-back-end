package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Product;
import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findAllByConsumerAndIsDeletedIsFalse(Consumer consumer);
    Page<ShoppingCart> findAllByConsumerAndIsDeletedIsFalse(Consumer consumer, Pageable pageable);

    @Query("select sc from ShoppingCart sc " +
            "join fetch sc.consumer c " +
            "join fetch sc.product p " +
            "join fetch c.profile pf " +
            "join fetch p.genre g " +
            "join fetch p.seller s " +
            "where c.id=:consumerId and p.id=:productId and sc.isDeleted=false ")
    Optional<ShoppingCart> findByConsumerIdProductId(Long consumerId, Long productId);
    Optional<ShoppingCart> findByConsumerAndProductAndIsDeletedIsFalse(Consumer consumer, Product product);

    @Query("delete from ShoppingCart sc " +
            "where sc.isDeleted=true")
    void hardDelete();
}
