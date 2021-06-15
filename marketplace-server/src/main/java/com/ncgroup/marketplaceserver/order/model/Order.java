package com.ncgroup.marketplaceserver.order.model;

import java.time.LocalDateTime;
import java.util.List;

import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	private long id;
	private Courier courier;
	private User user;
	private LocalDateTime delieveryTime;
	private String address;
	private OrderStatus status;
	private String comment;
	private boolean disturb;
	private float totalSum;
	private float discountSum;
	private List<OrderItem> items;
	
}
