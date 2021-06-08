package com.ncgroup.marketplaceserver.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Goods {
    private long id;
    private String name;
    private String category;
    private String description;
    private String image;
    private double price;
    private int quantity;
}
