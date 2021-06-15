package com.ncgroup.marketplaceserver.order.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ncgroup.marketplaceserver.goods.model.Unit;
import com.ncgroup.marketplaceserver.order.model.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemReadDto {

	private long id;
	private String name;
	private String category;
	private String firm;
	private int quantity;
	private float price;
	private Unit unit;
	
	public static OrderItemReadDto convertToDto(OrderItem item) {
		return OrderItemReadDto
				.builder()
				.id(item.getGood().getId())
				.name(item.getGood().getGoodName())
				.category(item.getGood().getCategoryName())
				.firm(item.getGood().getFirmName())
				.unit(item.getGood().getUnit())
				.price(item.getPrice())
				.quantity(item.getQuantity())
				.build();
	}
}
