package com.ncgroup.marketplaceserver.shopping.cart.model;

import com.ncgroup.marketplaceserver.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartItem {
    private long id;
    private long userId;
    private long goodsId;
    private int quantity;
    private LocalDateTime addingTime;

    public boolean belongsTo(User user){
        return user.getId()==userId;
    }
}
