package com.cts.ppstores.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleDto {
	
	@NotBlank(message = "Role is mandatory")
	private String role;
}
