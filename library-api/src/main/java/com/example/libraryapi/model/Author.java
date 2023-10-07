package com.example.libraryapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "authors")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Author {

    @Id
    @SequenceGenerator(name = "author_generator", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    private Long id;
    private UUID externalId;
    private String fullName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Book book;

}
