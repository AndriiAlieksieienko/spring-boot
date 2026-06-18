package com.andrii;

import com.andrii.model.Book;
import com.andrii.service.BookService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {
    private final BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setTitle("Hook");
                book.setAuthor("Arthur");
                book.setIsbn("987-3-21");
                book.setPrice(new BigDecimal("12.5"));
                book.setDescription("Great");
                book.setCoverImage("Hook");

                Book savedBook = bookService.save(book);

                System.out.println(bookService.findAll());
            }
        };
    }
}
