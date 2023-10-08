package com.example.libraryapi.repo;

import com.example.libraryapi.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = """
            SELECT
              bk.id,
              bk.external_id,
              bk.isbn,
              bk.title,
              bk.description,
              bk.book_status,
              bk.is_deleted,
              bk.genre,
              authors.full_name
            FROM
                books AS bk
                LEFT JOIN authors ON authors.book_id=bk.id
            WHERE
                bk.is_deleted=false
            """, nativeQuery = true)
    List<Book> findAll();

    @Query(value = """
            SELECT
              bk.id,
              bk.external_id,
              bk.isbn,
              bk.title,
              bk.description,
              bk.book_status,
              bk.is_deleted,
              bk.genre,
              authors.full_name
            FROM
                books AS bk
                LEFT JOIN authors ON authors.book_id=bk.id
            WHERE
                bk.is_deleted=false
            """, nativeQuery = true)
    Page<Book> findAll(Pageable pageable);


    @Query(value = """
            SELECT
              bk.id,
              bk.external_id,
              bk.isbn,
              bk.title,
              bk.description,
              bk.book_status,
              bk.is_deleted,
              bk.genre,
              authors.full_name
            FROM
                books AS bk
                LEFT JOIN authors ON authors.book_id=bk.id
            WHERE
                bk.book_status like 'AVAILABLE'
            """, nativeQuery = true)
    List<Book> findAvailableBooks();

    @Query(value = """
            SELECT
              bk.id,
              bk.external_id,
              bk.isbn,
              bk.title,
              bk.description,
              bk.book_status,
              bk.is_deleted,
              bk.genre,
              authors.full_name
            FROM
                books AS bk
                LEFT JOIN authors ON authors.book_id=bk.id
            WHERE
                bk.external_id=:externalId AND 
                bk.is_deleted=false
            """, nativeQuery = true)
    Optional<Book> findByExternalId(@Param("externalId") UUID externalId);

    @Query(value = """
            SELECT
              bk.id,
              bk.external_id,
              bk.isbn,
              bk.title,
              bk.description,
              bk.book_status,
              bk.is_deleted,
              bk.genre,
              authors.full_name
            FROM
                books AS bk
                LEFT JOIN authors ON authors.book_id=bk.id
            WHERE
                bk.isbn=:isbn AND 
                bk.is_deleted=false
            """, nativeQuery = true)
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

}
