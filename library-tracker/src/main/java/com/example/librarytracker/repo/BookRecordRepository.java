package com.example.librarytracker.repo;

import com.example.librarytracker.model.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRecordRepository extends JpaRepository<BookRecord, Long> {
}
