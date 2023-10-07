package com.example.libraryapi.exception;

public class MissingAuthHeaderException extends RuntimeException {
    public MissingAuthHeaderException(String message) {
        super(message);
    }
}
