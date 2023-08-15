package com.supercoding.shoppingmallbackend.service;

import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public void exampleErrorIntoService() {
        throw new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode());
    }

}
