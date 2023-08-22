package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Payment;
import com.supercoding.shoppingmallbackend.entity.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByOrderNumberAndIsDeletedIsFalse(String orderNumber);
    Page<Payment> findAllByConsumerAndIsDeletedIsFalseOrderByPaidAtDesc(Consumer consumer, Pageable pageable);
    List<Payment> findAllByConsumerAndIsDeletedIsFalseOrderByPaidAtDesc(Consumer consumer);
    List<Payment> findAllByConsumerAndOrderNumberIsInAndIsDeletedIsFalseOrderByPaidAtDesc(Consumer consumer, Collection<String> collection);
    Page<Payment> findAllByConsumerAndOrderNumberIsInAndIsDeletedIsFalseOrderByPaidAtDesc(Consumer consumer, Collection<String> collection, Pageable pageable);
    List<Payment> findAllByProductSellerAndIsDeletedIsFalseOrderByPaidAtDesc(Seller seller);
    List<Payment> findAllByProductSellerAndOrderNumberIsInAndIsDeletedIsFalseOrderByPaidAtDesc(Seller seller, Collection<String> collection);
    Page<Payment> findAllByProductSellerAndOrderNumberIsInAndIsDeletedIsFalseOrderByPaidAtDesc(Seller seller, Collection<String> collection, Pageable pageable);
    Page<Payment> findAllByProductSellerAndIsDeletedIsFalseOrderByPaidAtDesc(Seller seller, Pageable pageable);
}
