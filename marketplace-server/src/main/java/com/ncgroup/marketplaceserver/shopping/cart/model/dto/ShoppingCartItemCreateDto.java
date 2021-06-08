package com.ncgroup.marketplaceserver.shopping.cart.model.dto;

import com.ncgroup.marketplaceserver.model.Goods;
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
        Goods goods = shoppingCartItem.getGoods();
        if(goods==null){
            goods = Goods.builder().id(goodsId).build();
            shoppingCartItem.setGoods(goods);
        }else{
            goods.setId(goodsId);
        }
        shoppingCartItem.setQuantity(quantity);
        shoppingCartItem.setAddingTime(addingTime);
    }
}
