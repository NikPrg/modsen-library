package com.example.libraryapi.mapper;

public interface Mapper<T, R> {
    R map(T t);
}