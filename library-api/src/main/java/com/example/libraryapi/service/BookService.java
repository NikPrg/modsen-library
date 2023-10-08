package com.example.libraryapi.service;

import com.example.libraryapi.dto.BookInfoRequest;
import com.example.libraryapi.dto.PageRequestInfo;
import com.example.libraryapi.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll(PageRequestInfo request);
    List<Book> findAvailableBooks();
    Optional<Book> findByExternalId(String id);
    Optional<Book> findByIsbn(String isbn);
    void create(BookInfoRequest request);
    void update(BookInfoRequest request, String id);
    void delete(String id);
}
