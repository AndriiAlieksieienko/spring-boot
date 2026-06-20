package com.andrii.mapper;

import com.andrii.config.MapperConfig;
import com.andrii.dto.BookDto;
import com.andrii.dto.CreateBookRequestDto;
import com.andrii.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
