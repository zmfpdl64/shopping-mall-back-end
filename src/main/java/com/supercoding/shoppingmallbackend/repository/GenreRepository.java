package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
