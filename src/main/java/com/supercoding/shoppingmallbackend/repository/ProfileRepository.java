package com.supercoding.shoppingmallbackend.repository;

import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.supercoding.shoppingmallbackend.entity.Profile;
import com.supercoding.shoppingmallbackend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("SELECT p FROM Profile p where p.email =:email")
    Optional<Profile> findByEmail(@Param("email") String email);

    @Query("SELECT s from Seller s JOIN FETCH Profile p on p.id = s.profile_idx")
    Seller findByProfileId(Integer profileId);

    @Query("SELECT s from Seller s JOIN FETCH Profile p on p.id = s.profile_idx")
    Profile findByProfileId(Long profileId);
}
