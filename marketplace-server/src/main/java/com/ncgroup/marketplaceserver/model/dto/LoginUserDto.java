package com.ncgroup.marketplaceserver.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDto {
	@NotBlank(message = "Email is mandatory")
	@Email
	private String email;
	@NotBlank(message = "Password is mandatory")
	private String password;
	
}
