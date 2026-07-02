package com.andrii.service;

import com.andrii.dto.BookDto;
import com.andrii.dto.BookSearchParameters;
import com.andrii.dto.CreateBookRequestDto;
import com.andrii.dto.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    BookDto findById(Long id);

    Page<BookDto> findAll(Pageable pageable);

    void deleteById(Long id);

    BookDto updateBookById(Long id, UpdateBookRequestDto requestDto);

    List<BookDto> search(BookSearchParameters params);
}
