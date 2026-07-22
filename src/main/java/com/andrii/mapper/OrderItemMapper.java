package com.andrii.mapper;

import com.andrii.config.MapperConfig;
import com.andrii.dto.order.OrderItemDto;
import com.andrii.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = MapperConfig.class,
        uses = BookMapper.class
)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);
}
