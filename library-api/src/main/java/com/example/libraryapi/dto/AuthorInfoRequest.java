package com.example.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorInfoRequest(
        @NotBlank(message = "Author name must be provided")
        String fullname
) {
}
