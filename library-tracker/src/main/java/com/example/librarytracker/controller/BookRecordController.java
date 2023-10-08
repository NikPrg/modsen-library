package com.example.librarytracker.controller;

import com.example.librarytracker.model.BookRecord;
import com.example.librarytracker.service.BookRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.example.librarytracker.util.HttpUtils.PRIVATE_API_V1;

@RestController
@Slf4j
@RequestMapping(PRIVATE_API_V1)
@RequiredArgsConstructor
public class BookRecordController {
    private final BookRecordService bookRecordService;

    @PostMapping("/track")
    @ResponseStatus(HttpStatus.OK)
    public void trackBook(HttpEntity<UUID> entity){
        bookRecordService.save(entity);
    }

}
