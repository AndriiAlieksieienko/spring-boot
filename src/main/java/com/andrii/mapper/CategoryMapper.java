package com.andrii.mapper;

import com.andrii.config.MapperConfig;
import com.andrii.dto.category.CategoryDto;
import com.andrii.dto.category.CreateCategoryRequestDto;
import com.andrii.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto categoryDto);

    void updateCategoryFromDto(
            CreateCategoryRequestDto dto,
            @MappingTarget Category category
    );
}
