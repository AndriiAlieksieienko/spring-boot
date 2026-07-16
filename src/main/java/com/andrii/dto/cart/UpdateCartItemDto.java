package com.andrii.dto.cart;

import jakarta.validation.constraints.Min;

public record UpdateCartItemDto(
        @Min(1)
        int quantity
) {
}
