package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.PurchaseHistory;
import com.supercoding.shoppingmallbackend.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
}
