package com.ncgroup.marketplaceserver.order.model;


import com.ncgroup.marketplaceserver.goods.model.Good;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
	private Good good;
	private int quantity;
	private float price;
	
}
