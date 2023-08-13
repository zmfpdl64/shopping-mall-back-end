package com.supercoding.shoppingmallbackend.controller;

import com.supercoding.shoppingmallbackend.service.ExampleService;
import com.supercoding.shoppingmallbackend.common.CommonResponse;
import com.supercoding.shoppingmallbackend.common.Error.CustomException;
import com.supercoding.shoppingmallbackend.common.Error.domain.UserErrorCode;
import com.supercoding.shoppingmallbackend.common.util.ApiUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/example")
public class ExampleController {

    private final ExampleService exampleService;

    @AllArgsConstructor
    @Getter
    @Setter
    static class ExampleUser {
        private Long userIdx;
        private String username;
        private String nickname;
    }

    @GetMapping("/success")
    public CommonResponse<Object> howToSuccessResponse() {

        ExampleUser exampleUser = new ExampleUser(1L, "oneho", "원호");
        log.info(String.valueOf(exampleUser));
        return ApiUtils.success("유저 조회 성공", exampleUser);
    }

    @GetMapping("/fail")
    public CommonResponse<Object> howToFailResponse() {

        ExampleUser exampleUser = new ExampleUser(1L, "oneho", "원호");
        if (exampleUser.userIdx != 2) {
            throw new CustomException(UserErrorCode.NOTFOUND_USER.getErrorCode());
        }

        return ApiUtils.success("유저 조회 성공", exampleUser);
    }
    
    @GetMapping("/service/fail")
    public CommonResponse<Object> howtoFailServiceLayer() {
        exampleService.exampleErrorIntoService();
        return ApiUtils.success("코드가 여기 까지 실행되면 안댐 에러가 발생해야함", null);
    }

}
