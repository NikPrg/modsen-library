package com.example.libraryapi.model;

public enum BookStatus {
    IN_USE("In user"), // Книга занята
    AVAILABLE("Available");     // Книга доступна

    private String description;

    BookStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
