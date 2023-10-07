package com.example.libraryapi.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessagesConstants {
    public static String USER_NOT_FOUND_BY_EMAIL_ERROR_MESSAGE = "User wasn't found by given email = %s";
    public static String WRONG_CREDENTIALS_BY_EMAIL_ERROR_MESSAGE = "Bad credentials for email: %s";
    public static String USER_NOT_AUTHENTICATED_ERROR_MESSAGE = "User wasn't authenticated";
    public static String TOKEN_NOT_PROVIDED_ERROR_MESSAGE = "Authorization token isn't provided";
    public static String INVALID_TOKEN_ERROR_MESSAGE = "Invalid token has been provided";
    public static String AUTHORIZATION_HEADER_NOT_PROVIDED = "Authorization header must be provided";

    public static String BOOK_NOT_FOUND_BY_ID_EXCEPTION = "No book have been found by given id = %s";
    public static String BOOK_NOT_FOUND_BY_ISBN_EXCEPTION = "No book have been found by given isbn = %s";
    public static String BOOKS_NOT_FOUND_EXCEPTION = "No books have been found";

}
