package com.andrii.service.impl;

import com.andrii.dto.book.BookDto;
import com.andrii.dto.book.BookDtoWithoutCategoryIds;
import com.andrii.dto.book.BookSearchParameters;
import com.andrii.dto.book.CreateBookRequestDto;
import com.andrii.dto.book.UpdateBookRequestDto;
import com.andrii.exception.EntityNotFoundException;
import com.andrii.mapper.BookMapper;
import com.andrii.model.Book;
import com.andrii.model.Category;
import com.andrii.repository.book.BookRepository;
import com.andrii.repository.book.BookSpecificationBuilder;
import com.andrii.repository.category.CategoryRepository;
import com.andrii.service.BookService;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        List<Category> categoriesByIds = categoryRepository
                .findAllById(requestDto.getCategoryIds());
        book.setCategories(new HashSet<>(categoriesByIds));
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(Long id) {
        Book book = findBookById(id);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
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

        List<Category> categoriesByIds = categoryRepository
                .findAllById(requestDto.getCategoryIds());
        book.setCategories(new HashSet<>(categoriesByIds));
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId) {
        return bookRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
