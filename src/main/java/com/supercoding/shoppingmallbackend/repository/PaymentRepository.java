package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByOrderNumber(String orderNumber);

    @Query("select p from Payment p join fetch p.consumer c join fetch p.product pd where p.orderNumber=:orderNumber and p.isDeleted=false order by p.paidAt desc")
    List<Payment> findAllByOrderNumber(String orderNumber);

    @Query("select p from Payment p join fetch p.consumer c join fetch p.product pd where c.id=:consumerId and p.isDeleted=false order by p.paidAt desc")
    List<Payment> findAllByConsumerId(Long consumerId);
}
