package com.api.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {

    @Min(value = 4, message = "username required more than 4 letters.")
    @Max(value = 10, message = "username required less than 10 letters.")
    @Pattern(regexp = "[a-z0-9]]",message = "username only use alphabet lowercase, and 0-9 numbers.")
    private final String username;

    @Min(value = 8, message = "username required more than 8 letters.")
    @Max(value = 15, message = "username required less than 15 letters.")
    @Pattern(regexp = "[a-zA-Z0-9]]",message = "username only use alphabet, and 0-9 numbers.")
    private final String password;
}
