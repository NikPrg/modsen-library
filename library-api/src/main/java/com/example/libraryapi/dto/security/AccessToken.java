package com.example.libraryapi.dto.security;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.List;
@Builder
public record AccessToken(
        String tokenValue,
        Instant issuedAt,
        Instant expiredAt,
        List<GrantedAuthority> authorities
) {
}
