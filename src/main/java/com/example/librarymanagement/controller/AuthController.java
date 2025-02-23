package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.LoginRequestDto;
import com.example.librarymanagement.dto.LoginResponseDto;
import com.example.librarymanagement.dto.RegisterRequestDto;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/userregister")
    public User userRegister(@RequestBody RegisterRequestDto registerRequestDto){
        return authenticationService.userRegister(registerRequestDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return authenticationService.login(loginRequestDto);
    }

}
