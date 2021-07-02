package com.ncgroup.marketplaceserver.goods;

import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import com.ncgroup.marketplaceserver.goods.exceptions.GoodAlreadyExistsException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.GoodDto;
import com.ncgroup.marketplaceserver.goods.model.ModelView;
import com.ncgroup.marketplaceserver.goods.model.RequestParams;
import com.ncgroup.marketplaceserver.goods.service.GoodsService;

import java.util.List;


public abstract class Decorator implements GoodsService {
    GoodsService goodsService;

     public Decorator(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public Good create(GoodDto goodDto) throws GoodAlreadyExistsException {
        return goodsService.create(goodDto);
    }

    @Override
    public Good edit(GoodDto goodDto, long id) throws NotFoundException {
        return goodsService.edit(goodDto, id);
    }

    @Override
    public Good find(long id) throws NotFoundException {
        return goodsService.find(id);
    }

    @Override
    public ModelView display(RequestParams params) throws NotFoundException {
        return goodsService.display(params);
    }

    @Override
    public List<String> getCategories() throws NotFoundException {
        return goodsService.getCategories();
    }

    @Override
    public List<Double> getPriceRange(String category) throws NotFoundException {
        return goodsService.getPriceRange(category);
    }

    @Override
    public List<String> getFirms() throws NotFoundException {
        return goodsService.getFirms();
    }

    @Override
    public void updateQuantity(long id, double quantity) {
        goodsService.updateQuantity(id, quantity);
    }
}
