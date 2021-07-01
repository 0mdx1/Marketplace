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
    String sort;
    String direction;
    Integer page;
}
