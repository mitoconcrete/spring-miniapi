package com.app.api.dto.request;

import com.app.api.entity.UserRoleEnum;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class SignUpRequestDto {

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{4,10}$",
            message = "4자 이상 10자 이내여야하며, 하나 이상의 알파벳 소문자와 숫자의 조합으로 이뤄져야 합니다.")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@!%*#?&])[A-Za-z0-9$@!%*#?&]{8,15}$",
            message = "8자 이상 15자 이내여야하며, 하나 이상의 알파벳, 숫자, 특수문자의 조합으로 이뤄져야 합니다.")
    private String password;
    private final UserRoleEnum role = UserRoleEnum.USER;
}
