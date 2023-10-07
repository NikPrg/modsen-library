package com.example.libraryapi.dto;

public record UserLoginRequest(
        String email,
        String password
) {
}
