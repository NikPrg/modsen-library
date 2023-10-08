package com.example.libraryapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "books")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Book {

    @Id
    @SequenceGenerator(name = "book_generator", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    private Long id;
    private UUID externalId;
    private String isbn;
    private String title;
    private String description;
    private String genre;
    private boolean isDeleted;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Author> authors;

    @Enumerated(value = EnumType.STRING)
    private BookStatus bookStatus;

}
