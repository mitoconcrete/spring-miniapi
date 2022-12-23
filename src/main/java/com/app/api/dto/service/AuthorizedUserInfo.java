package com.app.api.dto.service;

import com.app.api.entity.User;
import com.app.api.entity.UserRoleEnum;
import com.app.api.utils.TokenType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorizedUserInfo {
    private final String username;
    private final UserRoleEnum role;
    private final TokenType tokenType;

    public User toEntity(){
        return new User(username, "" ,role);
    }
}

