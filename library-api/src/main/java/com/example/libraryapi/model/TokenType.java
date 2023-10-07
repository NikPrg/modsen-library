package com.example.libraryapi.model;

import lombok.Getter;

@Getter
public enum TokenType {
    BEARER("Bearer");
    private String value;
    TokenType(String value) {
        this.value = value;
    }
}
