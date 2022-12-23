package com.app.api.service;

import com.app.api.dto.response.JwtInfo;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationServiceInterface {
    JwtInfo getRenewTokens(String token);
}
