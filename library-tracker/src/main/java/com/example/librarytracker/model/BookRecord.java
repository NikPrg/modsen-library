package com.example.librarytracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Table(name = "book_records")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class BookRecord {

    @Id
    @SequenceGenerator(name = "book_records_generator", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_records_generator")
    private Long id;
    private UUID bookId;
    private LocalTime takenAt;
    private LocalTime expiredAt;

}
