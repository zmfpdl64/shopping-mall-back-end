package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    boolean existsByConsumerAndProductIdAndIsDeletedIsFalse(Consumer consumer, Long productId);

    List<Scrap> findAllByConsumerIdAndIsDeletedIsFalse(Consumer consumer);
}
