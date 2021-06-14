  package com.ncgroup.marketplaceserver.shopping.cart.model.dto;

import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.order.model.OrderItem;
import com.ncgroup.marketplaceserver.shopping.cart.annotation.SufficientGoodsStock;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import lombok.Data;
import javax.validation.constraints.Min;

@Data
@SufficientGoodsStock
public class ShoppingCartItemCreateDto {
    @Min(value = 1, message = "goodsId cannot be below 1")
    private long goodsId;
    @Min(value = 1, message = "quantity cannot be below 1")
    private int quantity;
    @Min(value = 1, message = "addingTime Unix timestamp cannot be below 1")
    private long addingTime;

    public void projectOnto(ShoppingCartItem shoppingCartItem){
        Good goods = shoppingCartItem.getGoods();
        if(goods==null){
            goods = Good.builder().id(goodsId).build();
            shoppingCartItem.setGoods(goods);
        }else{
            goods.setId(goodsId);
        }
        shoppingCartItem.setQuantity(quantity);
        shoppingCartItem.setAddingTime(addingTime);
    }
    
    public static OrderItem convertFromDto(ShoppingCartItemCreateDto itemDto) {
    	return OrderItem
    			.builder()
    			.good(Good
    					.builder()
    					.id(itemDto.getGoodsId())
    					.build()
    					)
    			.quantity(itemDto.getQuantity())
    			.build();
    }
}
