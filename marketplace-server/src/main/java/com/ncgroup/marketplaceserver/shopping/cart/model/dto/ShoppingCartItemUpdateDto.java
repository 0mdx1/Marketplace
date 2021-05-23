package com.ncgroup.marketplaceserver.shopping.cart.model.dto;

import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ShoppingCartItemUpdateDto {
    @Min(value = 1, message = "quantity cannot be below 1")
    private int quantity;

    public void mapTo(ShoppingCartItem shoppingCartItem){
        shoppingCartItem.setQuantity(quantity);
    }
}
