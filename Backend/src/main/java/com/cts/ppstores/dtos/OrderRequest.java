package com.cts.ppstores.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.validation.constraints.NotNull;

@Data
@Setter
@Getter
public class OrderRequest {

//    @NotNull(message = "Total price is mandatory")
//    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than zero")
//    private BigDecimal totalPrice;

    @NotNull(message = "Atleast One Item is mandatory")
    private List<OrderItemRequest> items;
}