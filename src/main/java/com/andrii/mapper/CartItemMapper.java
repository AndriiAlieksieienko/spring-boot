package com.andrii.mapper;

import com.andrii.config.MapperConfig;
import com.andrii.dto.cart.CartItemDto;
import com.andrii.dto.cart.CreateCartItemRequestDto;
import com.andrii.dto.cart.UpdateCartItemDto;
import com.andrii.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(
            target = "book",
            source = "bookId",
            qualifiedByName = "bookFromId"
    )
    @Mapping(target = "shoppingCart", ignore = true)
    CartItem toEntity(CreateCartItemRequestDto dto);

    @Mapping(target = "bookId",
            source = "book.id")
    @Mapping(target = "bookTitle",
            source = "book.title")
    CartItemDto toDto(CartItem cartItem);

    void updateCartFromDto(@MappingTarget CartItem cartItem,
                        UpdateCartItemDto dto);
}
