package com.mariesto.orderservice.model;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotNull
        String customerId
) {
}
