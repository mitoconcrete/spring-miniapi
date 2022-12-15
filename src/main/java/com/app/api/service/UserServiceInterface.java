package com.app.api.service;

import com.app.api.dto.request.UserRequestDto;

import javax.servlet.http.HttpServletResponse;

public interface UserServiceInterface {
    void createUser(UserRequestDto userRequestDto);
    void signInUser(UserRequestDto userRequestDto, HttpServletResponse response);
}
