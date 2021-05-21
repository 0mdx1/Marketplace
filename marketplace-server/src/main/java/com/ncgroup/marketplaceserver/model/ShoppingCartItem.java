package com.ncgroup.marketplaceserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ShoppingCartItem {
    private long id;
    private long credentialsId;
    private long goodsId;
    private LocalDateTime addingTime;
}
