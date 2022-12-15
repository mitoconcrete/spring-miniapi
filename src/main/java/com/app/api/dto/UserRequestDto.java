package com.app.api.dto;

import com.app.api.entity.UserRoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{4,10}$",
             message = "The username is combination of alphabet(lowercase), number.\nAnd letter length 4 to 10")
    private final String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@!%*#?&])[A-Za-z0-9$@!%*#?&]{8,15}$",
             message = "The password is combination of alphabet, number, special symbols.\nAnd letter length 8 to 15")
    private final String password;
    private final UserRoleEnum role;
}
