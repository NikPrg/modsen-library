package com.example.libraryapi.mapper;

import com.example.libraryapi.dto.AuthorInfoRequest;
import com.example.libraryapi.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements Mapper<AuthorInfoRequest, Author> {

    @Override
    public Author map(AuthorInfoRequest authorInfoRequest) {
        return Author.builder()
                .fullName(authorInfoRequest.fullname())
                .build();
    }
}
