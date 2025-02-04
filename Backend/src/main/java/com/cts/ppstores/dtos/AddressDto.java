package com.cts.ppstores.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id;

    @NotBlank(message = "Street is mandatory")
    private String street;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "State is mandatory")
    private String state;

    @NotBlank(message = "Zip Code is mandatory")
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Zip Code is invalid")
    private String zipCode;

    @NotBlank(message = "Country is mandatory")
    private String country;

    private UserDto user;

    private LocalDateTime createdAt;
}