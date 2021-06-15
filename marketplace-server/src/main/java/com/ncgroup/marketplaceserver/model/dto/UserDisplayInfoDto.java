package com.ncgroup.marketplaceserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDisplayInfoDto {
	
	private long id;
	private String name;
	private String surname;
	private String phone;

}
