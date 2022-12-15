package com.app.api.service;

import com.app.api.dto.request.UserRequestDto;
import com.app.api.entity.User;
import com.app.api.exception.DuplicateDataException;
import com.app.api.exception.NotFoundException;
import com.app.api.repository.UserRepository;
import com.app.api.utils.JwtUtil;
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
        // check same user in db.
        boolean isExist = userRepository.existsByUsername(userRequestDto.getUsername());

        // if exist same user, return error.
        if(isExist){
            throw new DuplicateDataException("중복된 username 입니다.");
        }

        // if not exist user in db, create new user by name, p/w, role.
        User user = new User(userRequestDto.getUsername(), userRequestDto.getPassword(), userRequestDto.getRole());

        // save in db.
        userRepository.save(user);
    }

    @Override
    public void signInUser(UserRequestDto userRequestDto, HttpServletResponse response) {
        // check in db
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(
                () -> new NotFoundException("회원을 찾을 수 없습니다.")
        );

        // check password
        if(!user.isPasswordValid(userRequestDto.getPassword())){
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }

        // token set in response header
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }
}
