package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.common.TimeTrace;
import com.supercoding.shoppingmallbackend.dto.request.AddressRequest;
import com.supercoding.shoppingmallbackend.dto.response.AddressResponse;
import com.supercoding.shoppingmallbackend.entity.Address;
import com.supercoding.shoppingmallbackend.entity.Profile;
import com.supercoding.shoppingmallbackend.repository.AddressRepository;
import com.supercoding.shoppingmallbackend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;


    @TimeTrace
    public void saveAddress(AddressRequest addressRequest, Long profileIdx) {

        Long validProfileIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));

        Profile profile = profileRepository.findById(validProfileIdx).orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));

        Optional<Address> optionalAddress = addressRepository.findByProfile(profile);
        Address saveAddress = Address.from(addressRequest, profile);
        optionalAddress.ifPresent(address -> saveAddress.setId(address.getId()));
        addressRepository.save(saveAddress);
    }

    @TimeTrace
    public AddressResponse findAddressByProfileId(Long profileIdx) {
        Long validProfileIdx = Optional.ofNullable(profileIdx)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));

        Profile profile = profileRepository.findById(validProfileIdx).orElseThrow(() -> new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode()));

        Optional<Address> optionalAddress = addressRepository.findByProfile(profile);
        if (optionalAddress.isPresent()) {
            return AddressResponse.from(optionalAddress.get());
        } else {
            return new AddressResponse();
        }
    }


}
