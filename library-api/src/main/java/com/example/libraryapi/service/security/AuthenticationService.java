package com.example.libraryapi.service.security;

import com.example.libraryapi.dto.security.AuthClientData;
import com.example.libraryapi.dto.security.UserLoginRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    AuthClientData authenticate(UserLoginRequest request);
    void deauthenticate(HttpServletRequest request);
    String isAuthenticated(String token);
}
