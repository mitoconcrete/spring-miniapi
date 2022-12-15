package com.app.api.service;

import com.app.api.dto.request.SignInRequestDto;
import com.app.api.dto.request.SignUpRequestDto;
import com.app.api.dto.response.JwtInfo;
import com.app.api.entity.User;
import com.app.api.entity.UserRefreshToken;
import com.app.api.exception.DuplicateDataException;
import com.app.api.exception.NotFoundException;
import com.app.api.repository.AuthorizationRepository;
import com.app.api.repository.UserRepository;
import com.app.api.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final AuthorizationRepository authorizationRepository;
    private final JwtUtil jwtUtil;
    @Override
    public void createUser(SignUpRequestDto userRequestDto) {
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
    public JwtInfo signInUser(SignInRequestDto userRequestDto) {
        // check in db
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(
                () -> new NotFoundException("회원을 찾을 수 없습니다.")
        );

        // check password
        if(!user.isPasswordValid(userRequestDto.getPassword())){
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }

        JwtInfo jwtInfo = jwtUtil.getAuthorizedTokens(user.getUsername(), user.getRole());
        Optional<UserRefreshToken> userRefreshToken = authorizationRepository.findByUser_Id(user.getId());

        // if already has data in db, update only token.
        if(userRefreshToken.isPresent()){
            userRefreshToken.get().updateToken(jwtInfo.getRefreshToken());
        }
        // if not data in db, create info.
        else{
            UserRefreshToken newUserRefreshToken = new UserRefreshToken(jwtInfo.getRefreshToken(),user);
            authorizationRepository.save(newUserRefreshToken);
        }

        return jwtInfo;
    }
}
