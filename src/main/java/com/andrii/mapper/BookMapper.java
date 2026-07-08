package com.andrii.mapper;

import com.andrii.config.MapperConfig;
import com.andrii.dto.BookDto;
import com.andrii.dto.CreateBookRequestDto;
import com.andrii.dto.UpdateBookRequestDto;
import com.andrii.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(
            UpdateBookRequestDto dto,
            @MappingTarget Book book
    );
}
