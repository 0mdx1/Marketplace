package com.ncgroup.marketplaceserver.goods.repository;

import com.ncgroup.marketplaceserver.goods.exceptions.GoodAlreadyExistsException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.GoodDto;
import com.ncgroup.marketplaceserver.goods.model.SearchParamsDto;

import java.util.*;

public interface GoodsRepository {
    Long createGood(GoodDto goodDto) throws GoodAlreadyExistsException;
    void editGood(GoodDto good, Long id);

    Optional<Good> findById(long id);
    List<Good> display(String query, SearchParamsDto params);
    Integer countGoods(String query, SearchParamsDto params);

    List<String> getCategories();
    List<String> getFirms();

    Double getMaxPrice(String category);
    Double getMinPrice(String category);
    Double getTotalMaxPrice();
    Double getTotalMinPrice();

    void editQuantity(long id, int quantity);
}
