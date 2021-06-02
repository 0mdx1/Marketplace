package com.ncgroup.marketplaceserver.shopping.cart.model;

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
    private long goodsId;
    private int quantity;
    private long addingTime;
}
