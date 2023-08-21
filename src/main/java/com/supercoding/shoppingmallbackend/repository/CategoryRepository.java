package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query("SELECT pc.category FROM ProductCategory pc WHERE pc.product.id = :productIdx ORDER BY pc.category.type desc")
    List<Category> findCategoriesByProductId(Long productIdx);

    @Query("SELECT c FROM Category c WHERE c.name in (:cateGoryNames) ORDER BY c.type")
    List<Category> findCategoriesByName(List<String> cateGoryNames);

}
