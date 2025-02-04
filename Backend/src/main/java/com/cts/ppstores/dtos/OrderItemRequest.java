package com.cts.ppstores.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotNull(message = "Product ID is mandatory")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}