package com.app.api.service;

import com.app.api.dto.response.JwtInfo;
import com.app.api.dto.service.AuthorizedUserInfo;
import com.app.api.entity.UserRoleEnum;
import com.app.api.exception.NotAuthorizedException;
import com.app.api.utils.JwtUtil;
import com.app.api.utils.TokenType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements AuthorizationServiceInterface{
    private final JwtUtil jwtUtil;

    public AuthorizedUserInfo getAuthorizedUserInfo(String token){
        if(jwtUtil.validateToken(token)){
            Claims claims = jwtUtil.getUserInfoFromToken(token);
            UserRoleEnum role = UserRoleEnum.valueOf(claims.get(JwtUtil.AUTHORIZATION_KEY).toString());
            TokenType tokenType = TokenType.valueOf(claims.get(JwtUtil.AUTHORIZATION_KEY).toString());
            return new AuthorizedUserInfo(claims.getSubject(), role, tokenType);
        }else{
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }

    public JwtInfo getRenewTokens(String token){
        // prev refresh token validation.
        AuthorizedUserInfo userInfo = getAuthorizedUserInfo(token);
        if(userInfo.getRole().equals(UserRoleEnum.USER) || userInfo.getTokenType().equals(TokenType.ACCESS)){
            throw new NotAuthorizedException("유효하지 않은 토큰입니다.");
        }

        return jwtUtil.getAuthorizedTokens(userInfo.getUsername(), userInfo.getRole());
    }
}
