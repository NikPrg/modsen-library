package com.example.libraryapi.service;

import com.example.libraryapi.config.properties.RestProperties;
import com.example.libraryapi.dto.BookInfoRequest;
import com.example.libraryapi.dto.PageRequestInfo;
import com.example.libraryapi.mapper.BookMapper;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.model.BookStatus;
import com.example.libraryapi.repo.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.libraryapi.utils.ExceptionMessagesConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepo;
    private final BookMapper bookMapper;
    private final RestTemplate restTemplate;
    private final RestProperties restProperties;

    @Override
    public List<Book> findAll(PageRequestInfo request) {
        val page = request.page();
        val amount = request.amount();

        if (ObjectUtils.isEmpty(page) && ObjectUtils.isEmpty(amount)) {
            var books = bookRepo.findAll();

            if (books.isEmpty()) log.error(BOOKS_NOT_FOUND_EXCEPTION);

            return bookRepo.findAll();
        }

        var pageableBooks = bookRepo.findAll(PageRequest.of(page, amount));

        if (pageableBooks.isEmpty()) log.error(BOOKS_NOT_FOUND_EXCEPTION);

        return pageableBooks.getContent();
    }

    @Override
    public List<Book> findAvailableBooks() {
        var books = bookRepo.findAvailableBooks();

        if (books.isEmpty()) log.error(BOOKS_NOT_FOUND_EXCEPTION);

        return books;
    }

    @Override
    public Optional<Book> findByExternalId(String id) {
        var book = bookRepo.findByExternalId(UUID.fromString(id));

        if (book.isEmpty()) log.error(BOOK_NOT_FOUND_BY_ID_EXCEPTION.formatted(id));

        return book;
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        var book = bookRepo.findByIsbn(isbn);

        if (book.isEmpty()) log.error(BOOK_NOT_FOUND_BY_ISBN_EXCEPTION.formatted(isbn));

        return book;
    }

    @Transactional
    @Override
    public void create(BookInfoRequest request) {
        val book = bookMapper.map(request);

        book.setBookStatus(BookStatus.AVAILABLE);
        book.getAuthors()
                .forEach(author -> author.setBook(book));

        restTemplate.postForObject(
                restProperties.libraryTrackerUrl(),
                new HttpEntity<>(book.getExternalId(), getHttpHeaders()),
                Void.class
        );

        bookRepo.save(book);
    }

    @Transactional
    @Override
    public void update(BookInfoRequest request, String id) {
        var storedBook = bookRepo.findByExternalId(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND_BY_ID_EXCEPTION.formatted(id)));
        var updatedBook = bookMapper.map(request);
        storedBook.setBookStatus(BookStatus.valueOf(request.bookStatus()));
        enrichStoredBook(storedBook, updatedBook);
    }

    @Transactional
    @Override
    public void delete(String id) {
        var storedBook = bookRepo.findByExternalId(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND_BY_ID_EXCEPTION.formatted(id)));
        storedBook.setDeleted(true);
        storedBook.setBookStatus(BookStatus.DELETED);
    }


    private void enrichStoredBook(Book stored, Book updated) {
        stored.getAuthors().clear();

        stored.setTitle(updated.getTitle());
        stored.setDescription(updated.getDescription());
        stored.setGenre(updated.getGenre());
        stored.getAuthors().addAll(updated.getAuthors());

        stored.getAuthors()
                .forEach(author -> author.setBook(stored));
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;

    }

    public static void main(String[] args) {
        boolean allPositiveNumber = isAllPositiveNumber("1", "1");
        System.out.println(allPositiveNumber);
    }

    public static boolean isAllPositiveNumber(String... arg){
        return Arrays.stream(arg)
                .allMatch(BookServiceImpl::isPositiveNumber);

    }

    public static boolean isPositiveNumber(String str) {
        if (str.equals("1")) {
            return true;
        } else {
            throw new IllegalArgumentException("Passed string isn't a number");
        }
    }
}
