package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    Optional<Consumer> findByIdAndIsDeletedIsFalse(Long consumerId);
    @Query("select p from Consumer c join fetch c.profile p where c.isDeleted=false and p.isDeleted=false and c.id=:consumerId limit 1")
    Optional<Consumer> findProfileByConsumerId(Long consumerId);
}
