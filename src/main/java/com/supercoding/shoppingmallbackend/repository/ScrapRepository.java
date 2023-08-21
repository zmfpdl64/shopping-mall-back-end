package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Consumer;
import com.supercoding.shoppingmallbackend.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    boolean existsByConsumerAndProductIdAndIsDeletedIsFalse(@NotNull Consumer consumer, @NotNull Long productId);

    List<Scrap> findAllByConsumerAndIsDeletedIsFalse(@NotNull Consumer consumer);
    Page<Scrap> findAllByConsumerAndIsDeletedIsFalse(@NotNull Consumer consumer, Pageable pageable);

    void deleteAllByIsDeletedIsTrue();
}
