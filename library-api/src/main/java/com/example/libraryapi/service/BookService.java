package com.example.libraryapi.service;

import com.example.libraryapi.dto.BookInfoRequest;
import com.example.libraryapi.dto.PageRequestInfo;
import com.example.libraryapi.mapper.AuthorMapper;
import com.example.libraryapi.mapper.BookMapper;
import com.example.libraryapi.model.Author;
import com.example.libraryapi.model.Book;
import com.example.libraryapi.model.BookStatus;
import com.example.libraryapi.repo.AuthorRepository;
import com.example.libraryapi.repo.BookRepository;
import com.example.libraryapi.utils.ISBNGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

import static com.example.libraryapi.utils.ExceptionMessagesConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
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

    public Optional<Book> findByExternalId(String id) {
        var book = bookRepo.findByExternalId(UUID.fromString(id));

        if (book.isEmpty()) log.error(BOOK_NOT_FOUND_BY_ID_EXCEPTION.formatted(id));

        return book;
    }

    public Optional<Book> findByIsbn(String isbn) {
        var book = bookRepo.findByIsbn(isbn);

        if (book.isEmpty()) log.error(BOOK_NOT_FOUND_BY_ISBN_EXCEPTION.formatted(isbn));

        return book;
    }

    @Transactional
    public void create(BookInfoRequest request) {
        val book = bookMapper.map(request);

        book.getAuthors()
                .forEach(author -> author.setBook(book));

        bookRepo.save(book);
//        authorRepo.saveAll(authors);
    }

    @Transactional
    public void update(BookInfoRequest request, String id){
        var storedBook = bookRepo.findByExternalId(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND_BY_ID_EXCEPTION.formatted(id)));

        var updatedBook = bookMapper.map(request);

        storedBook.setTitle(updatedBook.getTitle());
        storedBook.setDescription(updatedBook.getDescription());
        storedBook.setGenre(updatedBook.getGenre());
        storedBook.setAuthors(updatedBook.getAuthors());

        storedBook.getAuthors()
                .forEach(author -> author.setBook(storedBook));

        System.out.println("ge");
    }



//    private List<Author> buildAuthor(BookInfoRequest request, Book book){
//        return request.authors().stream()
//                .map(authorMapper::map)
//                .peek(author -> author.setBook(book))
//                .toList();
//    }
}
