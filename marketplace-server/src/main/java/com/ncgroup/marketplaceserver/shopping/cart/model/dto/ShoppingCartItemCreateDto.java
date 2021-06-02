package com.ncgroup.marketplaceserver.shopping.cart.model.dto;

import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ShoppingCartItemCreateDto {
    @Min(value = 1, message = "goodsId cannot be below 1")
    private long goodsId;
    @Min(value = 1, message = "quantity cannot be below 1")
    private int quantity;
    @Min(value = 1, message = "addingTime Unix timestamp cannot be below 1")
    private long addingTime;

    public void mapTo(ShoppingCartItem shoppingCartItem){
        shoppingCartItem.setGoodsId(goodsId);
        shoppingCartItem.setQuantity(quantity);
        shoppingCartItem.setAddingTime(addingTime);
    }
}
