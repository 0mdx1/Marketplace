package com.ncgroup.marketplaceserver.order.model;

import java.time.LocalDateTime;
import java.util.List;

import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.Unit;
import com.ncgroup.marketplaceserver.goods.model.Good.GoodBuilder;
import com.ncgroup.marketplaceserver.goods.model.GoodOrderDisplayDto;
import com.ncgroup.marketplaceserver.model.dto.UserDisplayInfoDto;

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
	private LocalDateTime delieveryTime;
	private String address;
	private boolean status;
	private String comment;
	private boolean disturb;
	private float totalSum; //total price without discount
	private float discountSum;//total price with discount
	private List<GoodOrderDisplayDto> goods;

}
