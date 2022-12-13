package com.api.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {

    @Pattern(regexp = "[a-z0-9]{4,10}$", message = "username use alphabet lowercase and numbers. And letter length 4 to 10")
    private final String username;

    @Pattern(regexp = "[a-zA-Z0-9]{8,15}$", message = "password use alphabet and 0-9 numbers. And letter length 8 to 15")
    private final String password;
}
