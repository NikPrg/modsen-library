package com.example.libraryapi.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(String id, String message) {
}