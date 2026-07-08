package com.andrii.service.impl;

import com.andrii.dto.book.BookDto;
import com.andrii.dto.book.BookSearchParameters;
import com.andrii.dto.book.CreateBookRequestDto;
import com.andrii.dto.book.UpdateBookRequestDto;
import com.andrii.exception.EntityNotFoundException;
import com.andrii.mapper.BookMapper;
import com.andrii.model.Book;
import com.andrii.repository.book.BookRepository;
import com.andrii.repository.book.BookSpecificationBuilder;
import com.andrii.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

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
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
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

    @Override
    public List<BookDto> search(BookSearchParameters params) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find the book by id: " + id));
    }
}
