package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p " +
            "join fetch p.genre " +
            "join fetch p.seller " +
            "where p.id=:productId and p.isDeleted=false " +
            "limit 1")
    Optional<Product> findProductById(Long productId);
}
