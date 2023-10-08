package com.example.libraryapi.model;

public enum BookStatus {
    IN_USE("In user"),
    AVAILABLE("Available"),
    DELETED("Deleted");

    private String description;

    BookStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
