package com.api.post.service;

import com.api.post.dto.UserRequestDto;
import com.api.post.entity.User;
import com.api.post.repository.UserRepository;
import com.api.post.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
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
    public void signInUser(UserRequestDto userRequestDto, HttpServletResponse response) {
        // check in db
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("not exist username.")
        );

        // check password
        if(!user.isPasswordValid(userRequestDto.getPassword())){
            throw new IllegalArgumentException("password invalid.");
        }

        // token set in response header
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
    }
}
