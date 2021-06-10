package com.ncgroup.marketplaceserver.shopping.cart.model;

import com.ncgroup.marketplaceserver.goods.model.Good;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartItem {
    private long userId;
    private Good goods;
    private int quantity;
    private long addingTime;
}