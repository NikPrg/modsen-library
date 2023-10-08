package com.example.libraryapi.mapper;

import com.example.libraryapi.dto.BookInfoRequest;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.utils.ISBNGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookMapper implements Mapper<BookInfoRequest, Book> {
    private final AuthorMapper authorMapper;

    @Override
    public Book map(BookInfoRequest request) {
        return Book.builder()
                .title(request.title())
                .description(request.description())
                .genre(request.genre())
                .authors(request.authors().stream()
                        .map(authorMapper::map)
                        .toList())
                .isbn(ISBNGenerator.generateISBN())
                .externalId(UUID.randomUUID())
                .isDeleted(false)
                .build();
    }
}
