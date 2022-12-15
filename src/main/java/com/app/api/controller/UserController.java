package com.app.api.controller;

import com.app.api.dto.UserRequestDto;
import com.app.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "작성자")
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "아이디, 패스워드, 역할을 입력하여 회원가입을 요청합니다.<br>아이디는 4-10자의 소문자+숫자조합 입니다.<br>패스워드는 8-15자의 알파벳 대문자+소문자+숫자 조합입니다.<br>역할은 0(관리자) 혹은 1(유저)로 구분됩니다.")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signUpUser(@RequestBody @Valid UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
        return "new user create complete.";
    }

    @ApiOperation(value = "로그인", notes = "아이디, 패스워드를 입력하여 로그인을 시도합니다.<br>로그인에 성공하면 응답헤더에 토큰이 발급됩니다.<br>다음 요청부터 ")
    @PostMapping("/signin")
    public String signInUser(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        userService.signInUser(userRequestDto, response);
        return "login complete.";
    }
}
