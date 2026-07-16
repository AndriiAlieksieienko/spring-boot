package com.andrii.controller;

import com.andrii.dto.cart.CreateCartItemRequestDto;
import com.andrii.dto.cart.ShoppingCartDto;
import com.andrii.dto.cart.UpdateCartItemDto;
import com.andrii.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart management", description = "Endpoints for managing carts")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a shopping cart")
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a book")
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto addBookShoppingCart(
            @RequestBody @Valid CreateCartItemRequestDto requestDto) {
        return shoppingCartService.addBook(requestDto);
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "Update a book quantity")
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartDto updateCartQuantity(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCartItemDto requestDto
    ) {
        return shoppingCartService.updateBookQuantity(id, requestDto);
    }

    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an item")
    @PreAuthorize("hasRole('USER')")
    public void deleteById(@PathVariable Long id) {
        shoppingCartService.deleteById(id);
    }
}
