package com.andrii.service;

import com.andrii.dto.cart.CreateCartItemRequestDto;
import com.andrii.dto.cart.ShoppingCartDto;
import com.andrii.dto.cart.UpdateCartItemDto;
import com.andrii.model.ShoppingCart;
import com.andrii.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addBook(CreateCartItemRequestDto requestDto);

    ShoppingCartDto updateBookQuantity(Long id, UpdateCartItemDto requestDto);

    void deleteById(Long id);

    ShoppingCart createCart(User user);
}
