package com.example.libraryapi.dto.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserLoginRequest(
        @NotBlank(message = "Email can not be blank")
        @Pattern(
                regexp = "^[a-zA-Z0-9_!#$%&'+=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
                message = "Incorrect email format"
        )
        String email,
        @NotBlank(message = "Password can not be blank")
        String password
) {
}
