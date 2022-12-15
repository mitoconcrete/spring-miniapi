package com.app.api.controller;

import com.app.api.dto.UserRequestDto;
import com.app.api.service.UserService;
import io.swagger.annotations.Api;
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
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signUpUser(@RequestBody @Valid UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
        return "new user create complete.";
    }

    @PostMapping("/signin")
    public String signInUser(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        userService.signInUser(userRequestDto, response);
        return "login complete.";
    }
}
