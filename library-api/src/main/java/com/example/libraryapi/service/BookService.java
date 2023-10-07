package com.example.libraryapi.service;

import com.example.libraryapi.dto.PageRequestInfo;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepo;

    public List<Book> findAll(PageRequestInfo request) {
        val page = request.page();
        val amount = request.amount();

        if (ObjectUtils.isEmpty(page) && ObjectUtils.isEmpty(amount)) {
            return bookRepo.findAll();
        }

        Page<Book> pageableBooks = bookRepo.findAll(PageRequest.of(page, amount));

        return pageableBooks.getContent();
    }
}
