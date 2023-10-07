package com.example.libraryapi.controller;

import com.example.libraryapi.dto.ErrorResponse;
import com.example.libraryapi.exception.AuthenticationException;
import com.example.libraryapi.exception.BadCredentialsException;
import com.example.libraryapi.exception.TokenNotProvidedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handeValidationException(EntityNotFoundException ex) {
        val errorId = UUID.randomUUID().toString();
        val message = ex.getMessage();
        if (log.isErrorEnabled()) {
            log.error("Handled entity not found error: msg='{}', errorId='{}", ex.getMessage(), errorId);
        }

        return ErrorResponse.builder()
                .id(errorId)
                .message(message)
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handeValidationException(BadCredentialsException ex) {
        val errorId = UUID.randomUUID().toString();
        val message = ex.getMessage();
        if (log.isErrorEnabled()) {
            log.error("Handled bad credentials error: msg='{}', errorId='{}", ex.getMessage(), errorId);
        }

        return ErrorResponse.builder()
                .id(errorId)
                .message(message)
                .build();
    }

    @ExceptionHandler(TokenNotProvidedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handeValidationException(TokenNotProvidedException ex) {
        val errorId = UUID.randomUUID().toString();
        val message = ex.getMessage();
        if (log.isErrorEnabled()) {
            log.error("Handled token not provided error: msg='{}', errorId='{}", ex.getMessage(), errorId);
        }

        return ErrorResponse.builder()
                .id(errorId)
                .message(message)
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handeValidationException(AuthenticationException ex) {
        val errorId = UUID.randomUUID().toString();
        val message = ex.getMessage();

        if (log.isErrorEnabled()) {
            log.error("Handled authentication error: msg='{}', errorId='{}", message, errorId);
        }

        return ErrorResponse.builder()
                .id(errorId)
                .message(message)
                .build();
    }

}
