package com.app.api.service;

import com.app.api.dto.request.SignInRequestDto;
import com.app.api.dto.request.SignUpRequestDto;
import com.app.api.dto.response.JwtInfo;

import javax.servlet.http.HttpServletResponse;

public interface UserServiceInterface {
    void createUser(SignUpRequestDto userRequestDto);
    JwtInfo signInUser(SignInRequestDto userRequestDto);
}
