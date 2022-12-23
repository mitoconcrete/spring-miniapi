package com.app.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class JwtInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
