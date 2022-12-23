package com.app.api.utils;

import com.app.api.dto.response.JwtInfo;
import com.app.api.entity.UserRoleEnum;
import com.app.api.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import io.jsonwebtoken.security.SignatureException;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String TOKEN_KEY = "token_type";
    private static final String BEARER_PREFIX = "Bearer";
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        throw new IllegalArgumentException("유저를 인증 할 수 없습니다.");
    }

    private String createToken(String username, UserRoleEnum role, TokenType type, Long expirationTime) {
        Date date = new Date();

        return Jwts.builder()
                .setSubject(username)
                .claim(TOKEN_KEY, type)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + expirationTime))
                .signWith(key, signatureAlgorithm)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }catch (ExpiredJwtException e){
            throw new TokenExpiredException("토큰 기한이 지났습니다.");
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public JwtInfo getAuthorizedTokens(String username, UserRoleEnum role){
        // 2hrs
        final Long ACCESS_TOKEN_TIME = 1000L * 60 * 60 * 2;

        // 30days
        final Long REFRESH_TOKEN_TIME = 1000L * 60 * 60 * 24 * 30;
        String accessToken = createToken(username, role, TokenType.ACCESS, ACCESS_TOKEN_TIME);
        String refreshToken = createToken(username, role, TokenType.REFRESH, REFRESH_TOKEN_TIME);
        return new JwtInfo(BEARER_PREFIX, accessToken, refreshToken);
    }

}