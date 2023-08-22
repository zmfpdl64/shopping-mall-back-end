package com.supercoding.shoppingmallbackend.repository;

import com.supercoding.shoppingmallbackend.common.TimeTrace;
import com.supercoding.shoppingmallbackend.entity.Address;
import com.supercoding.shoppingmallbackend.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @TimeTrace
    Optional<Address> findByProfile(Profile profile);

}
