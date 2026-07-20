package com.andrii.mapper;

import com.andrii.config.MapperConfig;
import com.andrii.dto.book.BookDto;
import com.andrii.dto.book.BookDtoWithoutCategoryIds;
import com.andrii.dto.book.CreateBookRequestDto;
import com.andrii.dto.book.UpdateBookRequestDto;
import com.andrii.model.Book;
import com.andrii.model.Category;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto requestDto);

    @Mapping(target = "categories", ignore = true)
    void updateBookFromDto(
            UpdateBookRequestDto dto,
            @MappingTarget Book book
    );

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        List<Long> categories = book.getCategories().stream()
                .map(Category::getId)
                .toList();
        bookDto.setCategoryIds(categories);
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
