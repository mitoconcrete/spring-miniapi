package com.app.api.utils;

import com.app.api.entity.UserRoleEnum;
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
    private static final String BEARER_PREFIX = "Bearer ";

    // 2hrs
    private static final int ACCESS_TOKEN_TIME = 60 * 60 * 2;

    // 30days
    private static final int REFRESH_TOKEN_TIME = 60 * 60 * 24 * 30;

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

    public String createAccessToken(String username, UserRoleEnum role){
        return createToken(username, role, ACCESS_TOKEN_TIME);
    }

    public String createRefreshToken(String username, UserRoleEnum role){
        return createToken(username, role, REFRESH_TOKEN_TIME);
    }

    private String createToken(String username, UserRoleEnum role, int expirationTime) {
        Date date = new Date();

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + expirationTime))
                .signWith(key, signatureAlgorithm)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}