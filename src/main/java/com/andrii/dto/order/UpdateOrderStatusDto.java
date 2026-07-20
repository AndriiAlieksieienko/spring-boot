package com.andrii.dto.order;

import com.andrii.model.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusDto(
        @NotNull
        Status status
) {
}
