package com.api.post.service;

import com.api.post.dto.UserRequestDto;

import javax.servlet.http.HttpServletResponse;

public interface UserServiceInterface {
    void createUser(UserRequestDto userRequestDto);
    void signInUser(UserRequestDto userRequestDto, HttpServletResponse response);
}
