package com.andrii.spring_boot.service;

import java.util.List;
import com.andrii.spring_boot.model.Book;

public interface BookService {
    Book save(Book book);
    List<Book> findAll();
}
