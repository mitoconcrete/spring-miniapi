package com.api.post.service;

import com.api.post.dto.UserRequestDto;

public interface UserServiceInterface {
    void createUser(UserRequestDto userRequestDto);
    void signInUser(UserRequestDto userRequestDto);
}
