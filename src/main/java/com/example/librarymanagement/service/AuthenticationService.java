package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.RegisterRequestDto;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public User userRegister(RegisterRequestDto registerRequestDto) {
        if(userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()){
            throw new RuntimeException("User Already Registered");
        }
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        return userRepository.save(user);
    }

    public User adminRegister(RegisterRequestDto registerRequestDto) {
        if(userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()){
            throw new RuntimeException("User Already Registered");
        }
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        return userRepository.save(user);
    }
}

