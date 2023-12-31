package com.example.libraryapi.service.security;

import com.example.libraryapi.dto.security.AccessToken;
import org.springframework.security.core.Authentication;

public interface TokenService {
    AccessToken createAccessToken(Authentication authentication, long expiresAtInMinutes);
}
