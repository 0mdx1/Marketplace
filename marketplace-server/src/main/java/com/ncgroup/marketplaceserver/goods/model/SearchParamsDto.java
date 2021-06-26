package com.ncgroup.marketplaceserver.goods.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParamsDto {
    String name;
    String category;
    Double minPrice;
    Double maxPrice;
    String sort;
    String direction;
    Integer page;
}
