package com.ncgroup.marketplaceserver.goods.service;

import com.ncgroup.marketplaceserver.goods.exceptions.GoodAlreadyExistsException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.GoodDto;
import com.ncgroup.marketplaceserver.exception.domain.NotFoundException;

import java.util.*;

public interface GoodsService {
    Good create(GoodDto goodDto) throws GoodAlreadyExistsException;
    Good edit(GoodDto goodDto, long id) throws NotFoundException;
    Good findById(long id) throws NotFoundException;

    Map<String, Object> display
            (String filter, String category, String minPrice, String maxPrice,
             String sortBy, String sortDirection, Integer page) throws NotFoundException;

    List<String> getCategories() throws NotFoundException;
    void updateQuantity(long id, int qunatity);
}

