package com.example.libraryapi.service.security.impl;

import com.example.libraryapi.config.properties.SecurityProperties;
import com.example.libraryapi.dto.security.AccessToken;
import com.example.libraryapi.service.security.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final SecurityProperties securityProperties;

    @Override
    public AccessToken createAccessToken(Authentication authentication, long expiresAtInMinutes) {
        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plus(expiresAtInMinutes, ChronoUnit.MINUTES);
        var authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        val tokenValue = buildTokenValue(authentication, issuedAt, expiredAt, authorities);

        return AccessToken.builder()
                .tokenValue(tokenValue)
                .issuedAt(issuedAt)
                .expiredAt(expiredAt)
                .authorities(authorities)
                .build();
    }

    private String buildTokenValue(Authentication authentication, Instant issuedAt, Instant expiredAt, List<GrantedAuthority> authorities) {
        return Jwts.builder()
                .setIssuedAt(Date.from(issuedAt))
                .setNotBefore(Date.from(issuedAt))
                .setExpiration(Date.from(expiredAt))
                .setSubject(authentication.getName())
                .claim("authorities", authorities)
                .claim("user-id", authentication.getDetails().toString())
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}