package com.example.librarymanagement.controller;

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
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')") // ✅ Use this
public class AdminController {


    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/adminregister")
    public User adminRegister(@RequestBody RegisterRequestDto registerRequestDto){
        return authenticationService.adminRegister(registerRequestDto);
    }


}
