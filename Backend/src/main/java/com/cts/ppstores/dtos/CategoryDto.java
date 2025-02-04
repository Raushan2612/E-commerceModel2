package com.cts.ppstores.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    
    @NotBlank(message = "Category name is mandatory")
    private String name;
    
    private List<ProductDto> productList;
}