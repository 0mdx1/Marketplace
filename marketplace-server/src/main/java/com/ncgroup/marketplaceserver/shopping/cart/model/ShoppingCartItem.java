package com.ncgroup.marketplaceserver.shopping.cart.model;

import com.ncgroup.marketplaceserver.model.Goods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartItem {
    private long userId;
    private Goods goods;
    private int quantity;
    private long addingTime;
}
