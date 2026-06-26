package com.andrii.service.impl;

import com.andrii.dto.BookDto;
import com.andrii.dto.CreateBookRequestDto;
import com.andrii.dto.UpdateBookRequestDto;
import com.andrii.exception.EntityNotFoundException;
import com.andrii.mapper.BookMapper;
import com.andrii.model.Book;
import com.andrii.repository.BookRepository;
import com.andrii.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto findById(Long id) {
        Book book = findBookById(id);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateBookById(Long id, UpdateBookRequestDto requestDto) {
        Book book = findBookById(id);
        bookMapper.updateBookFromDto(requestDto, book);

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find the book by id: " + id));
    }
}
