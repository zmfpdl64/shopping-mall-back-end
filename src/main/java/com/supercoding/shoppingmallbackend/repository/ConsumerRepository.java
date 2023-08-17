package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    Optional<Consumer> findByIdAndIsDeletedIsFalse(Long consumerId);
}
