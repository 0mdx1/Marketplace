package com.ncgroup.marketplaceserver.shopping.cart.model.dto;

import com.ncgroup.marketplaceserver.model.Goods;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartItemReadDto {
    private Goods goods;
    private int quantity;
    private long addingTime;

    public ShoppingCartItemReadDto(ShoppingCartItem item){
        this.goods=item.getGoods();
        this.quantity=item.getQuantity();
        this.addingTime=item.getAddingTime();
    }
}
