package com.example.libraryapi.service.security.impl;

import com.example.libraryapi.dto.AuthClientDetails;
import com.example.libraryapi.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.libraryapi.utill.ExceptionMessagesConstants.USER_NOT_FOUND_BY_EMAIL_ERROR_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws EntityNotFoundException {

        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_BY_EMAIL_ERROR_MESSAGE.formatted(email)));

        return new AuthClientDetails(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_".concat(user.getRoles()))),//todo: change to role
                user.getExternalId().toString()
        );
    }
}