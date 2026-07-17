package com.andrii.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCartItemRequestDto(
        @NotNull
        @Positive
        Long bookId,
        @Positive
        int quantity
) {
}
