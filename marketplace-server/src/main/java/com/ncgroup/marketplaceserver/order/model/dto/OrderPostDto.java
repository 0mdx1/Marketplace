package com.ncgroup.marketplaceserver.order.model.dto;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.order.model.Order;
import com.ncgroup.marketplaceserver.order.model.OrderItem;
import com.ncgroup.marketplaceserver.order.model.OrderStatus;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class OrderPostDto {
	private String name;
	private String surname;
	private String phone;
	private LocalDateTime deliveryTime;
	private String address;
	private String comment;
	private boolean disturb;
	private float totalSum;
	private float discountSum;

	@Valid
	private List<ShoppingCartItemCreateDto> items;
	
	public static Order toOrder(OrderPostDto orderDto) {
		log.info(orderDto.toString()); 
		Order order = Order.builder()
				.user(
						User.builder()
						.name(orderDto.getName())
						.surname(orderDto.getSurname())
						.phone(orderDto.getPhone())
						.build()
						)
				.delieveryTime(orderDto.getDeliveryTime())
				.address(orderDto.getAddress())
				.status(OrderStatus.SUBMITTED)
				.comment(orderDto.getComment())
				.disturb(orderDto.isDisturb())
				.totalSum(orderDto.getTotalSum())
				.discountSum(orderDto.getDiscountSum())
				.build();
		log.info(order.toString()); 
		List<OrderItem> orderItems = new LinkedList<>();
		for(ShoppingCartItemCreateDto cartItem : orderDto.getItems()) {
			orderItems.add(ShoppingCartItemCreateDto.convertFromDto(cartItem));
		}
		order.setItems(orderItems);
		
		return order;
	}
}
