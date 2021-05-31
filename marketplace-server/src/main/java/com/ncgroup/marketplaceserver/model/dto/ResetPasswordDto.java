package com.ncgroup.marketplaceserver.model.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
	private String link;
	private String password;
}
