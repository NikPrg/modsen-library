package com.example.libraryapi.repo;

import com.example.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByExternalId(UUID externalId);
    Optional<Book> findByIsbn(String isbn);
}
