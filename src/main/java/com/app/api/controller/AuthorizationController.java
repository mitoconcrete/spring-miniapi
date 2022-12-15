package com.app.api.controller;

import com.app.api.dto.response.AuthorizedResponse;
import com.app.api.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    @GetMapping("/token/renew")
    public AuthorizedResponse getRenewTokens(HttpServletRequest request){
        return new AuthorizedResponse(HttpStatus.CREATED, "토큰이 재발급 되었습니다.", authorizationService.getRenewTokens(request));
    }
}
