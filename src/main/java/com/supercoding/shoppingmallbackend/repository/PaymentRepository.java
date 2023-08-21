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

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByOrderNumberAndIsDeletedIsFalse(String orderNumber);
    List<Payment> findAllByOrderNumberAndIsDeletedIsFalse(String orderNumber);
    Page<Payment> findAllByConsumerAndIsDeletedIsFalse(Consumer consumer, Pageable pageable);
    List<Payment> findAllByConsumerAndIsDeletedIsFalse(Consumer consumer);

    @Query("select p from Payment p " +
            "join fetch p.consumer c " +
            "join fetch c.profile pf " +
            "join fetch p.product pd " +
            "join fetch pd.seller s " +
            "join fetch pd.genre g " +
            "where s.id=:sellerId and p.isDeleted=false " +
            "order by p.paidAt desc")
    List<Payment> findAllBySellerId(Long sellerId);

    @Query(value = "select p from Payment p " +
            "join fetch p.consumer c " +
            "join fetch c.profile pf " +
            "join fetch p.product pd " +
            "join fetch pd.seller s " +
            "join fetch pd.genre g " +
            "where s.id=:sellerId and p.isDeleted=false " +
            "order by p.paidAt desc ",
            countQuery = "select count(p) from Payment p " +
                    "where p.product.seller.id=:sellerId and p.isDeleted=false")
    Page<Payment> findAllBySellerId(Long sellerId, Pageable pageable);
}
