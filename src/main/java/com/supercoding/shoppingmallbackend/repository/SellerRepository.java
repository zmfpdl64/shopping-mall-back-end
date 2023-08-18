package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByProfile_Id(Long profileIdx);

    @Query("select s from Seller s " +
            "join fetch s.profile p " +
            "where p.id=:profileId and s.isDeleted=false")
    Optional<Seller> findByProfileId(Long profileId);
}
