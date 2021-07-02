package com.ncgroup.marketplaceserver.goods.repository;

import com.ncgroup.marketplaceserver.goods.exceptions.GoodAlreadyExistsException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.GoodDto;
import com.ncgroup.marketplaceserver.goods.model.RequestParams;

import java.util.*;

public interface GoodsRepository {
    Long getGoodId(GoodDto goodDto) throws GoodAlreadyExistsException;
    void editGood(GoodDto good, Long id);

    Optional<Good> findById(long id);
    List<Good> display(String query, RequestParams params);
    Integer countGoods(String query, RequestParams params);

    List<String> getCategories();
    List<String> getFirms();

    Double getMaxPrice(String category);
    Double getMinPrice(String category);
    Double getTotalMaxPrice();
    Double getTotalMinPrice();

    void editQuantity(long id, double quantity, boolean inStock);
}
