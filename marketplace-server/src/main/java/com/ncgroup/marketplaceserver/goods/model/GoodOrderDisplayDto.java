package com.ncgroup.marketplaceserver.goods.model;

import com.ncgroup.marketplaceserver.goods.model.Good.GoodBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodOrderDisplayDto {
	private long id;
	private String name;
	private String category;
	private String firm;
	private int quantity;
	private float price;
	private float discount;
	private Unit unit;

}
