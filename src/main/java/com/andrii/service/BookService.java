package com.andrii.service;

import com.andrii.dto.BookDto;
import com.andrii.dto.CreateBookRequestDto;
import com.andrii.dto.UpdateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    BookDto findById(Long id);

    List<BookDto> findAll();

    void deleteById(Long id);

    BookDto updateBookById(Long id, UpdateBookRequestDto requestDto);
}
