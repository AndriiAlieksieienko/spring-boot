package com.andrii.spring_boot.service.impl;

import com.andrii.spring_boot.model.Book;
import com.andrii.spring_boot.repository.BookRepository;
import com.andrii.spring_boot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return List.of();
    }
}
