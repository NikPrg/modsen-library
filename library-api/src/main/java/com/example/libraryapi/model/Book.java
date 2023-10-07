package com.example.libraryapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "books")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Book {

    @Id
    @SequenceGenerator(name = "book_generator", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    private Long id;
    private String isbn;
    private String title;
    private String description;
    private String genre;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Author> authors;

}
