package com.ncgroup.marketplaceserver.order.model.dto;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

import com.ncgroup.marketplaceserver.model.dto.UserDisplayInfoDto;
import com.ncgroup.marketplaceserver.order.model.Order;
import com.ncgroup.marketplaceserver.order.model.OrderItem;
import com.ncgroup.marketplaceserver.order.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReadDto {
	private long id;
	private UserDisplayInfoDto user;
	private OffsetDateTime deliveryTime;
	private String address;
	private OrderStatus status;
	private String comment;
	private boolean disturb;
	private float totalSum; //total price without discount
	private float discountSum;//total price with discount
	private List<OrderItemReadDto> goods;
	
	public static OrderReadDto convertToDto(Order order) {
		OrderReadDto orderReadDto = OrderReadDto
				.builder()
				.id(order.getId())
				.user(
						UserDisplayInfoDto
						.builder()
						.name(order.getUser().getName())
						.surname(order.getUser().getSurname())
						.phone(order.getUser().getPhone())
						.build()
						)
				.deliveryTime(order.getDeliveryTime())
				.address(order.getAddress())
				.status(order.getStatus())
				.comment(order.getComment())
				.disturb(order.isDisturb())
				.totalSum(order.getTotalSum())
				.build();
		List<OrderItemReadDto> items = new LinkedList<>();
		for(OrderItem item : order.getItems()) {
			items.add(OrderItemReadDto.convertToDto(item));
		}
		orderReadDto.setGoods(items);
		return orderReadDto;
	}

}
