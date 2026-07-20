package com.andrii.mapper;

import com.andrii.config.MapperConfig;
import com.andrii.dto.cart.ShoppingCartDto;
import com.andrii.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = MapperConfig.class,
        uses = CartItemMapper.class
)
public interface ShoppingCartMapper {
    @Mapping(target = "userId",
            source = "user.id")
    ShoppingCartDto toDto(ShoppingCart cart);
}
