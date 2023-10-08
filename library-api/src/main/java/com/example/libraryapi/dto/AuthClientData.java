package com.example.libraryapi.dto;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.List;

@Builder
public record AuthClientData(
        String id,
        String email,
        String accessToken,
        List<GrantedAuthority> authorities,
        Instant accessTokenExpiredAt
) {
}