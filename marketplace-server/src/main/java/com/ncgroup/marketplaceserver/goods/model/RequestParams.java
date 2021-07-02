package com.ncgroup.marketplaceserver.goods.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestParams {

    String name;
    String category;
    Double minPrice;
    Double maxPrice;
    SortCategory sort;
    String direction;
    Integer page;
}
