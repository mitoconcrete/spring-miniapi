package com.app.api.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizedResponse {
    private final HttpStatus status;
    private final String message;
    private JwtInfo authorizedPayload;

    public AuthorizedResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AuthorizedResponse(HttpStatus status, String message, JwtInfo jwtInfo){
        this.status = status;
        this.message = message;
        this.authorizedPayload = jwtInfo;
    }
}
