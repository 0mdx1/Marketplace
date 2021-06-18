package com.ncgroup.marketplaceserver.goods.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodDto {

    @NotEmpty
    private String goodName;
    @NotEmpty
    private String firmName;
    @Min(1)
    private int quantity;
    @Min(0)
    private double price;
    private Unit unit;
    @Min(1)
    @Max(99)
    private double discount;

    private String image;

//    @Past
//    private String shippingDate;

    private boolean inStock;
    private String description;
    @NotEmpty
    private String categoryName;
    private boolean status;

//    public void GoodDto(Good good) {
//
//    }
}
