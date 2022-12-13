package com.api.post.service;

import com.api.post.dto.UserRequestDto;
import com.api.post.entity.User;
import com.api.post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    @Override
    public void createUser(UserRequestDto userRequestDto) {
        boolean isExist = userRepository.existsByUsername(userRequestDto.getUsername());
        if(isExist){
            throw new IllegalArgumentException("user already exist.");
        }
        User user = new User(userRequestDto.getUsername(), userRequestDto.getPassword());
        userRepository.save(user);
    }

    @Override
    public void signInUser(UserRequestDto userRequestDto) {

    }
}
