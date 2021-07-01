package com.ncgroup.marketplaceserver.goods.service;

import com.ncgroup.marketplaceserver.goods.exceptions.GoodAlreadyExistsException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.GoodDto;
import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import com.ncgroup.marketplaceserver.goods.model.ModelView;
import com.ncgroup.marketplaceserver.goods.model.RequestParams;

import java.util.*;

public interface GoodsService {
    Good create(GoodDto goodDto) throws GoodAlreadyExistsException;
    Good edit(GoodDto goodDto, long id) throws NotFoundException;
    Good find(long id) throws NotFoundException;

    ModelView display(RequestParams params) throws NotFoundException;

    List<String> getCategories() throws NotFoundException;
    List<Double> getPriceRange(String category) throws NotFoundException;
    List<String> getFirms() throws NotFoundException;

    void updateQuantity(long id, double quantity);
}

