package com.example.librarytracker.service;

import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface BookRecordService {
    void save(HttpEntity<UUID> entity);
}
