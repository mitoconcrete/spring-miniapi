package com.app.api.utils;

import com.app.api.dto.service.AuthorizedUserInfo;
import com.app.api.exception.NotAuthorizedException;
import com.app.api.security.UserDetailsServiceImpl;
import com.app.api.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final AuthorizationService authorizationService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = jwtUtil.resolveToken(request);
        if(token.isPresent()){
            AuthorizedUserInfo userInfo = authorizationService.getAuthorizedUserInfo(token.get());
            if(userInfo.getTokenType().equals(TokenType.REFRESH)){
                throw new NotAuthorizedException("유효하지 않은 토큰입니다.");
            }
            setAuthentication(userInfo.getUsername());
        }
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(String username){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private void setAuthentication(String username){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
