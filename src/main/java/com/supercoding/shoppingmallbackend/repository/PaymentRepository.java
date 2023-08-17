package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByOrderNumber(String orderNumber);

    List<Payment> findByOrderNumberAndIsDeletedIsFalse(String orderNumber);

    List<Payment> findAllByConsumerIdAndIsDeletedIsFalse(Long consumerId);
}
