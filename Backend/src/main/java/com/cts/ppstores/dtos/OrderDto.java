package com.cts.ppstores.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	
    private Long id;

    @NotNull(message = "Total price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than zero")
    private BigDecimal totalPrice;

    @NotNull(message = "Order item list is mandatory")
    private List<OrderItemDto> orderItemList;
    
    private LocalDateTime createdAt;
}