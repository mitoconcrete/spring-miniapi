package com.app.api.service;

import com.app.api.dto.response.JwtInfo;
import com.app.api.entity.User;
import com.app.api.entity.UserRefreshToken;
import com.app.api.exception.NotAuthorizedException;
import com.app.api.exception.NotFoundException;
import com.app.api.repository.AuthorizationRepository;
import com.app.api.repository.UserRepository;
import com.app.api.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements AuthorizationServiceInterface{
    private final UserRepository userRepository;
    private final AuthorizationRepository authorizationRepository;
    private final JwtUtil jwtUtil;

    public JwtInfo getRenewTokens(HttpServletRequest request){
        // prev refresh token validation.
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(jwtUtil.validateToken(token)){
            claims = jwtUtil.getUserInfoFromToken(token);
        }else{
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }

        // get user
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new NotFoundException("유저가 존재하지 않습니다.")
        );

        // get prev refresh token
        UserRefreshToken userToken = authorizationRepository.findByUser_Id(user.getId()).orElseThrow(
                () -> new NotFoundException("유저에 해당되는 토큰 정보가 존재하지 않습니다.")
        );

        // if not same, delete refresh token info.
        boolean isValidToken = userToken.isTokenValid(token);
        if(!isValidToken){
            authorizationRepository.delete(userToken);
            throw new NotAuthorizedException("이상한 움직임이 감지되었습니다. 재 로그인해주세요.");
        }

        // if same, renew refresh token and update to db.
        JwtInfo jwtInfo = jwtUtil.getAuthorizedTokens(user.getUsername(), user.getRole());

        // update refresh token;
        userToken.updateToken(jwtInfo.getRefreshToken());
        return jwtInfo;
    }
}
