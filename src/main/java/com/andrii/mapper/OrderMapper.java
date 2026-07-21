package com.andrii.mapper;

import com.andrii.config.MapperConfig;
import com.andrii.dto.order.OrderDto;
import com.andrii.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = MapperConfig.class,
        uses = OrderItemMapper.class
)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);
}
