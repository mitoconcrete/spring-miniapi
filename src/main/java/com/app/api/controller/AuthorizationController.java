package com.app.api.controller;

import com.app.api.dto.response.AuthorizedResponse;
import com.app.api.service.AuthorizationService;
import com.app.api.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "인증")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final JwtUtil jwtUtil;
    @GetMapping("/token/renew")
    @ApiOperation(value = "토큰 재발급", notes = "Refresh Token 을 전달하여 새로운 토큰을 발급 받습니다.")
    public AuthorizedResponse getRenewTokens(HttpServletRequest request){
        String token = jwtUtil.resolveToken(request).get();
        return new AuthorizedResponse(HttpStatus.OK, "토큰이 재발급 되었습니다.", authorizationService.getRenewTokens(token));
    }
}
