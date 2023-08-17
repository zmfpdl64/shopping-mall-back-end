package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.PurchaseHistory;
import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    boolean existsByOrderNumber(String orderNumber);

    List<PurchaseHistory> findByOrderNumberAndIsDeletedIsFalse(String orderNumber);
}
