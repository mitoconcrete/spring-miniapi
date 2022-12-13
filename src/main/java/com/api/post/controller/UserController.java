package com.api.post.controller;

import com.api.post.dto.UserRequestDto;
import com.api.post.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public void createUser(@RequestBody @Valid UserRequestDto userRequestDto){
        System.out.println(userRequestDto.getUsername());
    }
}
