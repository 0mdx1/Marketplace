package com.ncgroup.marketplaceserver.order.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageDto {
	private List<OrderReadDto> orders;
	private int page;
	private int totalPages;
}
