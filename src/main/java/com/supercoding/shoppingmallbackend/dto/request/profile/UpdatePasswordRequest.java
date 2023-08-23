package com.supercoding.shoppingmallbackend.dto.request.profile;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePasswordRequest {

    @ApiModelProperty(value = "이메일", required = true, example = "asdf1234z@email.com1")
    private String email;
    @ApiModelProperty(value = "비밀번호", required = true, example = "asdf1234z")
    private String password;
    @ApiModelProperty(value = "비밀번호", required = true, example = "asdf1234z")
    private String updatePassword;
}
