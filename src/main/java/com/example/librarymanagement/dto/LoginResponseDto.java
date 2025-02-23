package com.example.librarymanagement.dto;

import lombok.Builder;

import javax.management.relation.Role;
import java.util.Set;


@Builder
public class LoginResponseDto {

    private String token;
    private String username;
    private Set<String> roles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
