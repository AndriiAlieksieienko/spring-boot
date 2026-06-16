package com.andrii.spring_boot.repository;

import java.util.List;
import com.andrii.spring_boot.model.Book;

public interface BookRepository {
    Book save(Book book);
    List<Book> findAll();
}
