package com.example.librarytracker.service.impl;

import com.example.librarytracker.config.TrackerProperties;
import com.example.librarytracker.model.BookRecord;
import com.example.librarytracker.repo.BookRecordRepository;
import com.example.librarytracker.service.BookRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookRecordServiceImpl implements BookRecordService {
    private final BookRecordRepository bookRecordRepo;
    private final TrackerProperties trackerProperties;

    @Override
    public void save(HttpEntity<UUID> entity) {
        val bookRecord = buildRecord(entity);
        bookRecordRepo.save(bookRecord);
    }

    private BookRecord buildRecord(HttpEntity<UUID> entity) {
        val takenAt = LocalTime.now();
        val expiredAt = takenAt.plusMinutes(trackerProperties.getBookExpirationTimeInMinutes());

        return BookRecord.builder()
                .bookId(entity.getBody())
                .takenAt(takenAt)
                .expiredAt(expiredAt)
                .build();
    }
}
