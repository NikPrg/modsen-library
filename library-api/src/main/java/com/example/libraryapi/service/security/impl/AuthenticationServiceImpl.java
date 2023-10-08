package com.example.libraryapi.service.security.impl;

import com.example.libraryapi.config.properties.SecurityProperties;
import com.example.libraryapi.dto.security.AuthClientData;
import com.example.libraryapi.dto.security.UserLoginRequest;
import com.example.libraryapi.exception.AuthenticationException;
import com.example.libraryapi.exception.TokenNotProvidedException;
import com.example.libraryapi.model.TokenType;
import com.example.libraryapi.service.security.AuthenticationService;
import com.example.libraryapi.service.security.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.libraryapi.utils.ExceptionMessagesConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    public static final Map<String, AuthClientData> AUTHORIZED_CLIENTS_DATA = new ConcurrentHashMap<>(256); //cache
    private final AuthenticationProvider userAuthenticationProvider;
    private final TokenService tokenService;
    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper;

    @Override
    public AuthClientData authenticate(UserLoginRequest request) {
        Authentication authentication = userAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        AuthClientData authorizedClient = getAuthClientData(authentication);

        AUTHORIZED_CLIENTS_DATA.put(authorizedClient.id(), authorizedClient);

        return authorizedClient;
    }

    @Override
    public void deauthenticate(HttpServletRequest request) {
        String accessToken = Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .map(token -> token.replaceFirst(TokenType.BEARER.getValue(), "").trim())
                .orElseThrow(() -> new TokenNotProvidedException(TOKEN_NOT_PROVIDED_ERROR_MESSAGE));

        String id = getClientId(accessToken);

        if (isAuthenticated(accessToken, id)) {
            AUTHORIZED_CLIENTS_DATA.remove(id);
        } else {
            throw new AuthenticationException(USER_NOT_AUTHENTICATED_ERROR_MESSAGE);
        }
    }

    @Override
    public String isAuthenticated(String token) {
        String id = getClientId(token);
        return isAuthenticated(token, id)
                ? id
                : StringUtils.EMPTY;
    }

    private boolean isAuthenticated(String token, String clientId) {
        if (StringUtils.isNotBlank(clientId)) {
            AuthClientData authenticatedUser = AUTHORIZED_CLIENTS_DATA.get(clientId);
            return authenticatedUser != null
                    && authenticatedUser.accessTokenExpiredAt().isAfter(Instant.now())
                    && token.equals(authenticatedUser.accessToken());
        }
        return false;
    }

    private AuthClientData getAuthClientData(Authentication authentication) {

        val email = authentication.getPrincipal().toString();
        val userId = authentication.getDetails().toString();

        if (authentication.isAuthenticated()) {
            val accessToken =
                    tokenService.createAccessToken(
                            authentication,
                            securityProperties.getAccessTokenLifetimeInMinutes()
                    );

            return AuthClientData.builder()
                    .id(userId)
                    .email(email)
                    .accessToken(accessToken.tokenValue())
                    .authorities(accessToken.authorities())
                    .accessTokenExpiredAt(accessToken.expiredAt())
                    .build();
        }
        throw new AuthenticationException(USER_NOT_AUTHENTICATED_ERROR_MESSAGE);
    }

    private String getClientId(String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        Map<String, Object> payload = Arrays.stream(token.split("\\."))
                .skip(1)
                .map(decoder::decode)
                .map(String::new)
                .map(this::convertToMap)
                .findFirst()
                .orElseThrow(() -> new AuthenticationException(USER_NOT_AUTHENTICATED_ERROR_MESSAGE));
        return (String) payload.getOrDefault("user-id", StringUtils.EMPTY);
    }

    private Map<String, Object> convertToMap(String payload) {
        try {
            return objectMapper.readValue(payload, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Could not parse the payload - '{}'", payload);
            throw new AuthenticationException(INVALID_TOKEN_ERROR_MESSAGE);
        }
    }
}
