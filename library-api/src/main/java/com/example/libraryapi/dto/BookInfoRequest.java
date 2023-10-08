package com.example.libraryapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record BookInfoRequest(
        @NotBlank(message = "Title can't be blank")
        String title,
        @NotBlank(message = "Description can't be blank")
        String description,
        @NotBlank(message = "Genre can't be blank")
        String genre,
        @NotBlank(message = "You should provide book status")
        String bookStatus,
        List<@Valid AuthorInfoRequest> authors
) {
}
